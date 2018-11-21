package ru.neoanon.openweather.data.source

import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import ru.neoanon.openweather.RxTestRule
import ru.neoanon.openweather.data.source.local.db.IDbSource
import ru.neoanon.openweather.data.source.local.db.currentweather.CurrentWeather
import ru.neoanon.openweather.data.source.local.db.dailyforecast.DailyForecast
import ru.neoanon.openweather.data.source.local.db.hourlyforecast.HourlyForecast
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation
import ru.neoanon.openweather.data.source.local.preference.ISettings
import ru.neoanon.openweather.data.source.remote.OpenWeatherMap
import ru.neoanon.openweather.data.source.remote.model.WeatherResponse
import ru.neoanon.openweather.model.CurrentWeatherItem
import ru.neoanon.openweather.model.enumerations.PressureType
import ru.neoanon.openweather.model.enumerations.UnitsType
import java.io.InputStreamReader
import java.util.*
import com.nhaarman.mockito_kotlin.any
import ru.neoanon.openweather.data.source.remote.model.DailyForecastResponse
import ru.neoanon.openweather.data.source.remote.model.HourlyForecastResponse
import ru.neoanon.openweather.utils.*

/**
 * Created by eshtefan on  20.11.2018.
 */

class ForecastsRepoTest {
    @JvmField
    @Rule
    val mockitoRule = MockitoJUnit.rule()
    @JvmField
    @Rule
    val rxTestRule = RxTestRule()

    @Mock
    lateinit var owm: OpenWeatherMap
    @Mock
    lateinit var iDbSource: IDbSource
    @Mock
    lateinit var iSettings: ISettings
    @Mock
    lateinit var modelTransformer: ModelTransformer
    @Mock
    lateinit var dateHandler: DateHandler

    private lateinit var iForecastsRepo: IForecastsRepo
    private lateinit var regionLocation: RegionLocation
    private val locale = Locale("en")
    private val regionId = 100L
    private val latitude = 10.4
    private val longitude = 14.7
    private val regionName = "Moscow"

    @Before
    fun setUp() {
        iForecastsRepo = ForecastsRepo(owm, iDbSource, iSettings, modelTransformer, dateHandler, locale)

        regionLocation = RegionLocation(1, regionName, latitude, longitude)
    }

    @Test
    fun updateDataWhenCacheDirty() {
        val stream = ClassLoader.getSystemResourceAsStream("weather_response.json")
        val reader = InputStreamReader(stream)
        val weatherResponse = Gson().fromJson(reader, WeatherResponse::class.java)

        val streamTwo = ClassLoader.getSystemResourceAsStream("hourly_forecast_response.json")
        val readerTwo = InputStreamReader(streamTwo)
        val hourlyForecastResponse = Gson().fromJson(readerTwo, HourlyForecastResponse::class.java)

        val streamThree = ClassLoader.getSystemResourceAsStream("daily_forecast_response.json")
        val readerThree = InputStreamReader(streamThree)
        val dailyForecastResponse = Gson().fromJson(readerThree, DailyForecastResponse::class.java)

        val dateOfDirtyCache = 100000L
        val currentTimestamp = 100500L

        whenever(iSettings.getDateOfDirtyCache()).then { dateOfDirtyCache }
        whenever(dateHandler.getCurrentTimestamp()).then { currentTimestamp }

        whenever(
            owm.getCurrentWeather(
                regionLocation.latitude,
                regionLocation.longitude,
                OWM_API_KEY,
                UnitsType.METRIC.id,
                TYPE_OF_ACCURACY,
                locale.language
            )
        ).then { Observable.just(weatherResponse) }
        whenever(iSettings.getPressureType()).then { PressureType.TORR.id }
        whenever(iDbSource.saveWeather(any())).then { 1L }

        whenever(dateHandler.getCurrentHour()).then { 8 }
        whenever(dateHandler.getLocalOffset()).then { 0 }
        whenever(
            owm.getHourlyForecast(
                regionLocation.latitude,
                regionLocation.longitude,
                OWM_API_KEY,
                UnitsType.METRIC.id,
                TYPE_OF_ACCURACY,
                locale.language,
                13
            )
        ).then { Observable.just(hourlyForecastResponse) }
        whenever(iDbSource.deleteHourlyForecasts(regionLocation.id)).then { 1 }
        whenever(iDbSource.saveHourlyForecasts(any())).then { 1 }

        whenever(
            owm.getDailyForecast(
                regionLocation.latitude,
                regionLocation.longitude,
                OWM_API_KEY,
                UnitsType.METRIC.id,
                TYPE_OF_ACCURACY,
                locale.language,
                NUMBER_OF_DAILY_FORECASTS
            )
        ).then { Observable.just(dailyForecastResponse) }
        whenever(iDbSource.deleteDailyForecasts(regionLocation.id)).then { 1 }
        whenever(iDbSource.saveDailyForecasts(any())).then { 1 }
        com.nhaarman.mockito_kotlin.doNothing().whenever(iSettings).saveDateOfDirtyCache(ArgumentMatchers.anyLong())

        iForecastsRepo.updateData(regionLocation)
            .test()
            .assertValue { it.name == regionLocation.name }

        assertTrue(dateOfDirtyCache < currentTimestamp)
    }

    @Test
    fun updateDataWhenCacheNotDirty() {
        val dateOfDirtyCache = 100500L
        val currentTimestamp = 100000L
        whenever(iSettings.getDateOfDirtyCache()).then { dateOfDirtyCache }
        whenever(dateHandler.getCurrentTimestamp()).then { currentTimestamp }
        iForecastsRepo.updateData(regionLocation)
            .test()
            .assertComplete()
            .assertNoValues()

        assertTrue(dateOfDirtyCache > currentTimestamp)

        verify(owm, never()).getCurrentWeather(
            ArgumentMatchers.anyDouble(),
            ArgumentMatchers.anyDouble(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
    }

    @Test
    fun updateDataWhenCacheIsNotInit() {
        val dateOfDirtyCache = -1L
        whenever(iSettings.getDateOfDirtyCache()).then { dateOfDirtyCache }
        iForecastsRepo.updateData(regionLocation)
            .test()
            .assertNotComplete()

        verify(owm, times(1)).getCurrentWeather(
            ArgumentMatchers.anyDouble(),
            ArgumentMatchers.anyDouble(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
    }

    @Test
    fun deleteAllInfoForThisRegion() {
        iForecastsRepo.deleteAllInfoForThisRegion(regionId)
            .test()
            .assertNoErrors()

        verify(iDbSource, times(1)).deleteLocation(regionId)
        verify(iDbSource, times(1)).deleteWeather(regionId)
        verify(iDbSource, times(1)).deleteDailyForecasts(regionId)
        verify(iDbSource, times(1)).deleteHourlyForecasts(regionId)
    }

    @Test
    fun getAllShortDailyForecastsReturnNull() {
        whenever(iDbSource.loadAllDailyForecastForRegion(regionId)).then { Flowable.just(listOf<DailyForecast>()) }
        iForecastsRepo.getAllShortDailyForecasts(regionId)
            .test()
            .assertValue { it.isEmpty() }
    }

    @Test
    fun getAllCommonHourlyForecastsReturnEmpty() {
        whenever(iDbSource.loadAllHourlyForecastForRegion(regionId)).then { Flowable.just(listOf<HourlyForecast>()) }
        iForecastsRepo.getAllCommonHourlyForecasts(regionId)
            .test()
            .assertValue { it.isEmpty() }
    }

    @Test
    fun getCurrentWeatherReturnNotEmpty() {
        val currentWeather = CurrentWeather(0, "", 0, "", 0, 0, 0, "")
        val weatherItem = CurrentWeatherItem("", 0, "", 0)
        whenever(iDbSource.loadWeatherById(regionId)).then { Flowable.just(listOf(currentWeather)) }
        whenever(modelTransformer.getWeatherItem(currentWeather)).then { weatherItem }
        iForecastsRepo.getCurrentWeather(regionId)
            .test()
            .assertValue { it == weatherItem }
    }

    @Test
    fun getCurrentWeatherReturnEmpty() {
        whenever(iDbSource.loadWeatherById(regionId)).then { Flowable.just(listOf<CurrentWeather>()) }
        iForecastsRepo.getCurrentWeather(regionId)
            .test()
            .assertValue { it.iconId == 0 && it.backgroundIconId == 0 && it.temp == "" && it.weatherDescription == "" }
    }

    @Test
    fun deleteAllForecasts() {
        iForecastsRepo.deleteAllForecasts()

        verify(iDbSource, times(1)).deleteAllWeathers()
        verify(iDbSource, times(1)).deleteAllDailyForecasts()
        verify(iDbSource, times(1)).deleteAllHourlyForecasts()
    }
}
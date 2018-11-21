package ru.neoanon.openweather.data.source

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import ru.neoanon.openweather.data.source.local.db.IDbSource
import ru.neoanon.openweather.data.source.local.db.currentweather.CurrentWeather
import ru.neoanon.openweather.data.source.local.db.dailyforecast.DailyForecast
import ru.neoanon.openweather.data.source.local.db.hourlyforecast.HourlyForecast
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation
import ru.neoanon.openweather.data.source.local.preference.ISettings
import ru.neoanon.openweather.data.source.remote.OpenWeatherMap
import ru.neoanon.openweather.model.*
import ru.neoanon.openweather.model.enumerations.TempType
import ru.neoanon.openweather.model.enumerations.UnitsType
import ru.neoanon.openweather.utils.*
import java.util.*
import kotlin.collections.ArrayList

/**
 *Created by eshtefan on  14.11.2018.
 */

class ForecastsRepo(
    val owm: OpenWeatherMap,
    val iDbSource: IDbSource,
    val iSettings: ISettings,
    val modelTransformer: ModelTransformer,
    val dateHandler: DateHandler,
    val locale: Locale
) : IForecastsRepo {
    override fun getRegionLocation(regionId: Long): Observable<RegionLocation> =
        Observable.fromCallable { iDbSource.getLocation(regionId) }

    override fun getRegionLocation(regionName: String): RegionLocation =
        iDbSource.getLocation(regionName)

    override fun addRegionLocation(regionLocation: RegionLocation): Long =
        iDbSource.saveLocation(regionLocation)

    override fun updateData(regionLocation: RegionLocation): Observable<RegionLocation> =
        Observable.fromCallable {
            iSettings.getDateOfDirtyCache()
        }.filter { timestamp -> ((timestamp == -1L) || (timestamp < dateHandler.getCurrentTimestamp())) }
            .flatMap {
                owm.getCurrentWeather(
                    regionLocation.latitude,
                    regionLocation.longitude,
                    OWM_API_KEY,
                    if (iSettings.getTempType() == TempType.KELVIN.id) UnitsType.STANDARD.id else UnitsType.METRIC.id,
                    TYPE_OF_ACCURACY,
                    locale.language
                )
            }
            .map { response ->
                val currentWeather = CurrentWeather.fromOWM(
                    response,
                    iSettings.getPressureType(),
                    regionLocation.id
                )
                iDbSource.saveWeather(currentWeather)
            }
            .flatMap {
                owm.getHourlyForecast(
                    regionLocation.latitude,
                    regionLocation.longitude,
                    OWM_API_KEY,
                    if (iSettings.getTempType() == TempType.KELVIN.id) UnitsType.STANDARD.id else UnitsType.METRIC.id,
                    TYPE_OF_ACCURACY,
                    locale.language,
                    HourlyForecast.getNumberOfHourlyForecast(dateHandler.getCurrentHour(), dateHandler.getLocalOffset())
                )
            }
            .map { response ->
                val forecasts = ArrayList<HourlyForecast>()
                for (i in response.list) {
                    forecasts.add(HourlyForecast.fromOWM(i, regionLocation.id))
                }
                iDbSource.deleteHourlyForecasts(regionLocation.id)
                iDbSource.saveHourlyForecasts(forecasts)
                1
            }
            .flatMap {
                owm.getDailyForecast(
                    regionLocation.latitude,
                    regionLocation.longitude,
                    OWM_API_KEY,
                    if (iSettings.getTempType() == TempType.KELVIN.id) UnitsType.STANDARD.id else UnitsType.METRIC.id,
                    TYPE_OF_ACCURACY,
                    locale.language,
                    NUMBER_OF_DAILY_FORECASTS
                )
            }
            .map { response ->
                val forecasts = ArrayList<DailyForecast>()
                val pressureType = iSettings.getPressureType()
                for (i in response.list) {
                    forecasts.add(DailyForecast.fromOWM(i, pressureType, regionLocation.id))
                }
                iDbSource.deleteDailyForecasts(regionLocation.id)
                iDbSource.saveDailyForecasts(forecasts)

                val dateOfDirtyCache = dateHandler.getCurrentTimestamp() + CACHE_LIFETIME
                iSettings.saveDateOfDirtyCache(dateOfDirtyCache)
                regionLocation
            }

    override fun getAllRegionLocations(): Flowable<List<RegionLocation>> = iDbSource.loadAllLocations()

    override fun deleteAllInfoForThisRegion(id: Long): Completable =
        Observable.fromCallable {
            iDbSource.deleteLocation(id)
            iDbSource.deleteWeather(id)
            iDbSource.deleteDailyForecasts(id)
            iDbSource.deleteHourlyForecasts(id)
        }
            .ignoreElements()

    override fun getAllShortDailyForecasts(regionId: Long): Flowable<List<DailyForecastShortItem>> =
        iDbSource.loadAllDailyForecastForRegion(regionId)
            .flatMap { dailyForecasts ->
                if (dailyForecasts.isEmpty()) {
                    Flowable.just(listOf<DailyForecastShortItem>())
                } else {
                    val tempType = iSettings.getTempType()
                    val forecasts = arrayListOf<DailyForecastShortItem>()
                    for (i in dailyForecasts) {
                        forecasts.add(modelTransformer.getDailyShortItem(i, tempType))
                    }
                    Flowable.just(forecasts)
                }
            }

    override fun getAllCommonHourlyForecasts(regionId: Long): Flowable<List<CommonHourlyItem>> =
        iDbSource.loadAllHourlyForecastForRegion(regionId)
            .flatMap { hourlyForecasts ->
                if (hourlyForecasts.isEmpty()) {
                    val emptyList = listOf<CommonHourlyItem>()
                    Flowable.just(emptyList)
                } else {
                    val currentWeather = iDbSource.getWeather(regionId)
                    val unit = iSettings.getUnit()
                    val forecasts = arrayListOf<CommonHourlyItem>()
                    forecasts.add(modelTransformer.getHeaderHourlyForecastItem(currentWeather, unit.pressureType))
                    forecasts.add(modelTransformer.getFirstHourlyForecastItem(currentWeather, unit.tempType))
                    for (i in hourlyForecasts) {
                        forecasts.add(modelTransformer.getHourlyForecastItem(i, unit.tempType))
                    }
                    Flowable.just(forecasts)
                }
            }

    override fun getCurrentWeather(regionId: Long): Flowable<CurrentWeatherItem> =
        iDbSource.loadWeatherById(regionId)
            .flatMap { currentWeathers ->
                if (currentWeathers.isEmpty())
                    Flowable.just(CurrentWeatherItem("", 0, "", 0))
                else
                    Flowable.just(modelTransformer.getWeatherItem(currentWeathers[0]))
            }

    override fun getAllDailyForecasts(regionId: Long): Observable<List<DailyForecastItem>> =
        Observable.fromCallable {
            val resultList = arrayListOf<DailyForecastItem>()
            val dailyForecasts = iDbSource.loadAllDailyForecasts(regionId)
            val pressureType = iSettings.getPressureType()
            for (i in dailyForecasts) {
                resultList.add(modelTransformer.getDailyForecastItem(i, pressureType))
            }
            resultList
        }

    override fun getUnits(): Observable<UnitsOfWeather> = Observable.fromCallable { iSettings.getUnit() }

    override fun setUnits(unit: UnitsOfWeather) {
        iSettings.saveTempType(unit.tempType)
        iSettings.savePressureType(unit.pressureType)
    }

    override fun deleteAllForecasts() {
        iDbSource.deleteAllWeathers()
        iDbSource.deleteAllDailyForecasts()
        iDbSource.deleteAllHourlyForecasts()
    }

    override fun makeCacheDirty() {
        iSettings.saveDateOfDirtyCache(dateHandler.getCurrentTimestamp() - 60)
    }
}
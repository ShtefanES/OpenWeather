package ru.neoanon.openweather.utils

import android.app.Application
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import ru.neoanon.openweather.R
import ru.neoanon.openweather.model.enumerations.CardinalPoints
import ru.neoanon.openweather.model.enumerations.PressureType

/**
 * Created by eshtefan on  20.11.2018.
 */

class ResourceProviderTest {
    private lateinit var resourceProvider: ResourceProvider
    @JvmField
    @Rule
    val mockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var app: Application

    @Before
    fun setUp() {
        resourceProvider = ResourceProvider(app)
    }

    @Test
    fun getLocalizedWindDirectionWhenN() {
        whenever(app.getString(R.string.north)).then { "test" }
        resourceProvider.getLocalizedWindDirection(CardinalPoints.N.name)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.north)
    }

    @Test
    fun getLocalizedWindDirectionWhenNE() {
        whenever(app.getString(R.string.north_east)).then { "test" }
        resourceProvider.getLocalizedWindDirection(CardinalPoints.NE.name)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.north_east)
    }

    @Test
    fun getLocalizedWindDirectionWhenE() {
        whenever(app.getString(R.string.east)).then { "test" }
        resourceProvider.getLocalizedWindDirection(CardinalPoints.E.name)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.east)
    }

    @Test
    fun getLocalizedWindDirectionWhenSE() {
        whenever(app.getString(R.string.south_east)).then { "test" }
        resourceProvider.getLocalizedWindDirection(CardinalPoints.SE.name)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.south_east)
    }

    @Test
    fun getLocalizedWindDirectionWhenS() {
        whenever(app.getString(R.string.south)).then { "test" }
        resourceProvider.getLocalizedWindDirection(CardinalPoints.S.name)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.south)
    }

    @Test
    fun getLocalizedWindDirectionWhenSW() {
        whenever(app.getString(R.string.south_west)).then { "test" }
        resourceProvider.getLocalizedWindDirection(CardinalPoints.SW.name)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.south_west)
    }

    @Test
    fun getLocalizedWindDirectionWhenW() {
        whenever(app.getString(R.string.west)).then { "test" }
        resourceProvider.getLocalizedWindDirection(CardinalPoints.W.name)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.west)
    }

    @Test
    fun getLocalizedWindDirectionWhenNW() {
        whenever(app.getString(R.string.north_west)).then { "test" }
        resourceProvider.getLocalizedWindDirection(CardinalPoints.NW.name)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.north_west)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getLocalizedWindDirectionWhenBadArgument() {
        resourceProvider.getLocalizedWindDirection("illegal arg")
    }

    @Test
    fun getWindIconIdWhenN() {
        val expectedId = resourceProvider.getWindIconId(CardinalPoints.N.name)
        val actualId = R.drawable.ic_n
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getWindIconIdWhenNE() {
        val expectedId = resourceProvider.getWindIconId(CardinalPoints.NE.name)
        val actualId = R.drawable.ic_ne
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getWindIconIdWhenE() {
        val expectedId = resourceProvider.getWindIconId(CardinalPoints.E.name)
        val actualId = R.drawable.ic_e
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getWindIconIdWhenSE() {
        val expectedId = resourceProvider.getWindIconId(CardinalPoints.SE.name)
        val actualId = R.drawable.ic_se
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getWindIconIdWhenS() {
        val expectedId = resourceProvider.getWindIconId(CardinalPoints.S.name)
        val actualId = R.drawable.ic_s
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getWindIconIdWhenSW() {
        val expectedId = resourceProvider.getWindIconId(CardinalPoints.SW.name)
        val actualId = R.drawable.ic_sw
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getWindIconIdWhenW() {
        val expectedId = resourceProvider.getWindIconId(CardinalPoints.W.name)
        val actualId = R.drawable.ic_w
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getWindIconIdWhenNW() {
        val expectedId = resourceProvider.getWindIconId(CardinalPoints.NW.name)
        val actualId = R.drawable.ic_nw
        assertEquals(expectedId, actualId)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getWindIconIdWhenBadArgument() {
        resourceProvider.getWindIconId("illegal arg")
    }

    @Test
    fun getMiddleWindIconIdWhenN() {
        val expectedId = resourceProvider.getMiddleWindIconId(CardinalPoints.N.name)
        val actualId = R.drawable.ic_n_middle
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getMiddleWindIconIdWhenNE() {
        val expectedId = resourceProvider.getMiddleWindIconId(CardinalPoints.NE.name)
        val actualId = R.drawable.ic_ne_middle
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getMiddleWindIconIdWhenE() {
        val expectedId = resourceProvider.getMiddleWindIconId(CardinalPoints.E.name)
        val actualId = R.drawable.ic_e_middle
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getMiddleWindIconIdWhenSE() {
        val expectedId = resourceProvider.getMiddleWindIconId(CardinalPoints.SE.name)
        val actualId = R.drawable.ic_se_middle
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getMiddleWindIconIdWhenS() {
        val expectedId = resourceProvider.getMiddleWindIconId(CardinalPoints.S.name)
        val actualId = R.drawable.ic_s_middle
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getMiddleWindIconIdWhenSW() {
        val expectedId = resourceProvider.getMiddleWindIconId(CardinalPoints.SW.name)
        val actualId = R.drawable.ic_sw_middle
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getMiddleWindIconIdWhenW() {
        val expectedId = resourceProvider.getMiddleWindIconId(CardinalPoints.W.name)
        val actualId = R.drawable.ic_w_middle
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getMiddleWindIconIdWhenNW() {
        val expectedId = resourceProvider.getMiddleWindIconId(CardinalPoints.NW.name)
        val actualId = R.drawable.ic_nw_middle
        assertEquals(expectedId, actualId)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getMiddleWindIconIdWhenBadArgument() {
        resourceProvider.getMiddleWindIconId("illegal arg")
    }

    @Test
    fun getDailyForecastIconIdWhenDayClouds() {
        val expectedId = resourceProvider.getDailyForecastIconId("03d")
        val actualId = R.drawable.d30
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenNightClouds() {
        val expectedId = resourceProvider.getDailyForecastIconId("03n")
        val actualId = R.drawable.d30
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenDayBrokenClouds() {
        val expectedId = resourceProvider.getDailyForecastIconId("04d")
        val actualId = R.drawable.d40
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenNightBrokenClouds() {
        val expectedId = resourceProvider.getDailyForecastIconId("04n")
        val actualId = R.drawable.d40
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenDayShowerRain() {
        val expectedId = resourceProvider.getDailyForecastIconId("09d")
        val actualId = R.drawable.d90
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenNightShowerRain() {
        val expectedId = resourceProvider.getDailyForecastIconId("09n")
        val actualId = R.drawable.d90
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenDayThunderstorm() {
        val expectedId = resourceProvider.getDailyForecastIconId("11d")
        val actualId = R.drawable.d11
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenNightThunderstorm() {
        val expectedId = resourceProvider.getDailyForecastIconId("11n")
        val actualId = R.drawable.d11
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenDaySnow() {
        val expectedId = resourceProvider.getDailyForecastIconId("13d")
        val actualId = R.drawable.d31
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenNightSnow() {
        val expectedId = resourceProvider.getDailyForecastIconId("13n")
        val actualId = R.drawable.d31
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenDayMist() {
        val expectedId = resourceProvider.getDailyForecastIconId("50d")
        val actualId = R.drawable.d05
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenNightMist() {
        val expectedId = resourceProvider.getDailyForecastIconId("50n")
        val actualId = R.drawable.d05
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenClearSky() {
        val expectedId = resourceProvider.getDailyForecastIconId("01d")
        val actualId = R.drawable.d10
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenFewClouds() {
        val expectedId = resourceProvider.getDailyForecastIconId("02d")
        val actualId = R.drawable.d20
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenNightClearSky() {
        val expectedId = resourceProvider.getDailyForecastIconId("01n")
        val actualId = R.drawable.n10
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenNightFewClouds() {
        val expectedId = resourceProvider.getDailyForecastIconId("02n")
        val actualId = R.drawable.n20
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenNightRain() {
        val expectedId = resourceProvider.getDailyForecastIconId("10n")
        val actualId = R.drawable.n01
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getDailyForecastIconIdWhenDayRain() {
        val expectedId = resourceProvider.getDailyForecastIconId("10d")
        val actualId = R.drawable.d01
        assertEquals(expectedId, actualId)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getDailyForecastIconIWhenBadArgument() {
        resourceProvider.getDailyForecastIconId("illegal arg")
    }

    @Test
    fun getCurrentWeatherIconIdWhenDayClouds() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("03d")
        val actualId = R.drawable.big_d30
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenNightClouds() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("03n")
        val actualId = R.drawable.big_d30
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenDayBrokenClouds() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("04d")
        val actualId = R.drawable.big_d40
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenNightBrokenClouds() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("04n")
        val actualId = R.drawable.big_d40
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenDayShowerRain() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("09d")
        val actualId = R.drawable.big_d90
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenNightShowerRain() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("09n")
        val actualId = R.drawable.big_d90
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenDayThunderstorm() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("11d")
        val actualId = R.drawable.big_d11
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenNightThunderstorm() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("11n")
        val actualId = R.drawable.big_d11
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenDaySnow() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("13d")
        val actualId = R.drawable.big_d31
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenNightSnow() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("13n")
        val actualId = R.drawable.big_d31
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenDayMist() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("50d")
        val actualId = R.drawable.big_d05
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenNightMist() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("50n")
        val actualId = R.drawable.big_d05
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenClearSky() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("01d")
        val actualId = R.drawable.big_d10
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenFewClouds() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("02d")
        val actualId = R.drawable.big_d20
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenNightClearSky() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("01n")
        val actualId = R.drawable.big_n10
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenNightFewClouds() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("02n")
        val actualId = R.drawable.big_n20
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenNightRain() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("10n")
        val actualId = R.drawable.big_n01
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getCurrentWeatherIconIdWhenDayRain() {
        val expectedId = resourceProvider.getCurrentWeatherIconId("10d")
        val actualId = R.drawable.big_d01
        assertEquals(expectedId, actualId)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCurrentWeatherIconIdWhenBadArgument() {
        resourceProvider.getCurrentWeatherIconId("illegal arg")
    }

    @Test
    fun getBackgroundWeatherIdWhenDayClouds() {
        val expectedId = resourceProvider.getBackgroundWeatherId("03d")
        val actualId = R.drawable.ic_background_clouds
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenNightClouds() {
        val expectedId = resourceProvider.getBackgroundWeatherId("03n")
        val actualId = R.drawable.ic_background_clouds
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenDayBrokenClouds() {
        val expectedId = resourceProvider.getBackgroundWeatherId("04d")
        val actualId = R.drawable.ic_background_clouds
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIddWhenNightBrokenClouds() {
        val expectedId = resourceProvider.getBackgroundWeatherId("04n")
        val actualId = R.drawable.ic_background_clouds
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenDayShowerRain() {
        val expectedId = resourceProvider.getBackgroundWeatherId("09d")
        val actualId = R.drawable.ic_background_shower_rain
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenNightShowerRain() {
        val expectedId = resourceProvider.getBackgroundWeatherId("09n")
        val actualId = R.drawable.ic_background_shower_rain
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenDayThunderstorm() {
        val expectedId = resourceProvider.getBackgroundWeatherId("11d")
        val actualId = R.drawable.ic_background_shower_rain
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenNightThunderstorm() {
        val expectedId = resourceProvider.getBackgroundWeatherId("11n")
        val actualId = R.drawable.ic_background_shower_rain
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenDaySnow() {
        val expectedId = resourceProvider.getBackgroundWeatherId("13d")
        val actualId = R.drawable.ic_background_snow
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenNightSnow() {
        val expectedId = resourceProvider.getBackgroundWeatherId("13n")
        val actualId = R.drawable.ic_background_snow
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenDayMist() {
        val expectedId = resourceProvider.getBackgroundWeatherId("50d")
        val actualId = R.drawable.ic_background_clouds
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenNightMist() {
        val expectedId = resourceProvider.getBackgroundWeatherId("50n")
        val actualId = R.drawable.ic_background_clouds
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenClearSky() {
        val expectedId = resourceProvider.getBackgroundWeatherId("01d")
        val actualId = R.drawable.ic_background_clear
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenFewClouds() {
        val expectedId = resourceProvider.getBackgroundWeatherId("02d")
        val actualId = R.drawable.ic_background_clear
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenNightClearSky() {
        val expectedId = resourceProvider.getBackgroundWeatherId("01n")
        val actualId = R.drawable.ic_background_clear
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenNightFewClouds() {
        val expectedId = resourceProvider.getBackgroundWeatherId("02n")
        val actualId = R.drawable.ic_background_clear
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenNightRain() {
        val expectedId = resourceProvider.getBackgroundWeatherId("10n")
        val actualId = R.drawable.ic_background_rain
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getBackgroundWeatherIdWhenDayRain() {
        val expectedId = resourceProvider.getBackgroundWeatherId("10d")
        val actualId = R.drawable.ic_background_rain
        assertEquals(expectedId, actualId)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getBackgroundWeatherIdWhenBadArgument() {
        resourceProvider.getBackgroundWeatherId("illegal arg")
    }


    @Test
    fun getHourlyForecastIconIdWhenDayClouds() {
        val expectedId = resourceProvider.getHourlyForecastIconId("03d")
        val actualId = R.drawable.white_d30
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenNightClouds() {
        val expectedId = resourceProvider.getHourlyForecastIconId("03n")
        val actualId = R.drawable.white_d30
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenDayBrokenClouds() {
        val expectedId = resourceProvider.getHourlyForecastIconId("04d")
        val actualId = R.drawable.white_d40
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenNightBrokenClouds() {
        val expectedId = resourceProvider.getHourlyForecastIconId("04n")
        val actualId = R.drawable.white_d40
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenDayShowerRain() {
        val expectedId = resourceProvider.getHourlyForecastIconId("09d")
        val actualId = R.drawable.white_d90
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenNightShowerRain() {
        val expectedId = resourceProvider.getHourlyForecastIconId("09n")
        val actualId = R.drawable.white_d90
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenDayThunderstorm() {
        val expectedId = resourceProvider.getHourlyForecastIconId("11d")
        val actualId = R.drawable.white_d11
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenNightThunderstorm() {
        val expectedId = resourceProvider.getHourlyForecastIconId("11n")
        val actualId = R.drawable.white_d11
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenDaySnow() {
        val expectedId = resourceProvider.getHourlyForecastIconId("13d")
        val actualId = R.drawable.white_d31
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenNightSnow() {
        val expectedId = resourceProvider.getHourlyForecastIconId("13n")
        val actualId = R.drawable.white_d31
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenDayMist() {
        val expectedId = resourceProvider.getHourlyForecastIconId("50d")
        val actualId = R.drawable.white_d05
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenNightMist() {
        val expectedId = resourceProvider.getHourlyForecastIconId("50n")
        val actualId = R.drawable.white_d05
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenClearSky() {
        val expectedId = resourceProvider.getHourlyForecastIconId("01d")
        val actualId = R.drawable.white_d10
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenFewClouds() {
        val expectedId = resourceProvider.getHourlyForecastIconId("02d")
        val actualId = R.drawable.white_d20
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenNightClearSky() {
        val expectedId = resourceProvider.getHourlyForecastIconId("01n")
        val actualId = R.drawable.white_n10
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenNightFewClouds() {
        val expectedId = resourceProvider.getHourlyForecastIconId("02n")
        val actualId = R.drawable.white_n20
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenNightRain() {
        val expectedId = resourceProvider.getHourlyForecastIconId("10n")
        val actualId = R.drawable.white_n01
        assertEquals(expectedId, actualId)
    }

    @Test
    fun getHourlyForecastIconIdWhenDayRain() {
        val expectedId = resourceProvider.getHourlyForecastIconId("10d")
        val actualId = R.drawable.white_d01
        assertEquals(expectedId, actualId)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getHourlyForecastIconIdWhenBadArgument() {
        resourceProvider.getHourlyForecastIconId("illegal arg")
    }

    @Test
    fun getRainStr() {
        whenever(app.getString(R.string.millimeters_of_precipitation, "12")).then { "test" }
        resourceProvider.getRainStr(12)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.millimeters_of_precipitation, "12")
    }

    @Test
    fun getRainStrWhenNull() {
        val result = resourceProvider.getRainStr(0)
        assertNull(result)
    }

    @Test
    fun getSnowStr() {
        whenever(app.getString(R.string.millimeters_of_precipitation, "17")).then { "test" }
        resourceProvider.getSnowStr(17)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.millimeters_of_precipitation, "17")
    }

    @Test
    fun getSnowStrWhenNull() {
        val result = resourceProvider.getSnowStr(0)
        assertNull(result)
    }

    @Test
    fun getPressureStrWhenTorr() {
        whenever(app.getString(R.string.pressure_mm_hg, "177")).then { "test" }
        resourceProvider.getPressureStr(177, PressureType.TORR.id)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.pressure_mm_hg, "177")
    }

    @Test
    fun getPressureStrWhenGectopascal() {
        whenever(app.getString(R.string.pressure_hectopascal, "1000")).then { "test" }
        resourceProvider.getPressureStr(1000, PressureType.HECTOPASCAL.id)

        Mockito.verify(app, Mockito.times(1)).getString(R.string.pressure_hectopascal, "1000")
    }
}
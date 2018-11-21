package ru.neoanon.openweather.utils

import android.app.Application
import ru.neoanon.openweather.R
import ru.neoanon.openweather.model.enumerations.CardinalPoints
import ru.neoanon.openweather.model.enumerations.PressureType
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by eshtefan on  13.11.2018.
 */
@Singleton
class ResourceProvider @Inject constructor(val app: Application) {

    fun getLocalizedWindDirection(direction: String): String =
        when (direction) {
            CardinalPoints.N.name -> app.getString(R.string.north)
            CardinalPoints.NE.name -> app.getString(R.string.north_east)
            CardinalPoints.E.name -> app.getString(R.string.east)
            CardinalPoints.SE.name -> app.getString(R.string.south_east)
            CardinalPoints.S.name -> app.getString(R.string.south)
            CardinalPoints.SW.name -> app.getString(R.string.south_west)
            CardinalPoints.W.name -> app.getString(R.string.west)
            CardinalPoints.NW.name -> app.getString(R.string.north_west)
            else -> throw  IllegalArgumentException()
        }

    fun getWindIconId(direction: String): Int =
        when (direction) {
            CardinalPoints.N.name -> R.drawable.ic_n
            CardinalPoints.NE.name -> R.drawable.ic_ne
            CardinalPoints.E.name -> R.drawable.ic_e
            CardinalPoints.SE.name -> R.drawable.ic_se
            CardinalPoints.S.name -> R.drawable.ic_s
            CardinalPoints.SW.name -> R.drawable.ic_sw
            CardinalPoints.W.name -> R.drawable.ic_w
            CardinalPoints.NW.name -> R.drawable.ic_nw
            else -> throw  IllegalArgumentException()
        }

    fun getMiddleWindIconId(direction: String): Int =
        when (direction) {
            CardinalPoints.N.name -> R.drawable.ic_n_middle
            CardinalPoints.NE.name -> R.drawable.ic_ne_middle
            CardinalPoints.E.name -> R.drawable.ic_e_middle
            CardinalPoints.SE.name -> R.drawable.ic_se_middle
            CardinalPoints.S.name -> R.drawable.ic_s_middle
            CardinalPoints.SW.name -> R.drawable.ic_sw_middle
            CardinalPoints.W.name -> R.drawable.ic_w_middle
            CardinalPoints.NW.name -> R.drawable.ic_nw_middle
            else -> throw  IllegalArgumentException()
        }

    fun getDailyForecastIconId(iconId: String): Int =
        if (iconId == "03d" || iconId == "03n") {
            R.drawable.d30
        } else if (iconId == "04d" || iconId == "04n") {
            R.drawable.d40
        } else if (iconId == "09d" || iconId == "09n") {
            R.drawable.d90
        } else if (iconId == "11d" || iconId == "11n") {
            R.drawable.d11
        } else if (iconId == "13d" || iconId == "13n") {
            R.drawable.d31
        } else if (iconId == "50d" || iconId == "50n") {
            R.drawable.d05
        } else if (iconId == "01d") {
            R.drawable.d10
        } else if (iconId == "02d") {
            R.drawable.d20
        } else if (iconId == "10d") {
            R.drawable.d01
        } else if (iconId == "01n") {
            R.drawable.n10
        } else if (iconId == "02n") {
            R.drawable.n20
        } else if (iconId == "10n") {
            R.drawable.n01
        } else {
            throw IllegalArgumentException()
        }

    fun getCurrentWeatherIconId(iconId: String): Int =
        if (iconId == "03d" || iconId == "03n") {
            R.drawable.big_d30
        } else if (iconId == "04d" || iconId == "04n") {
            R.drawable.big_d40
        } else if (iconId == "09d" || iconId == "09n") {
            R.drawable.big_d90
        } else if (iconId == "11d" || iconId == "11n") {
            R.drawable.big_d11
        } else if (iconId == "13d" || iconId == "13n") {
            R.drawable.big_d31
        } else if (iconId == "50d" || iconId == "50n") {
            R.drawable.big_d05
        } else if (iconId == "01d") {
            R.drawable.big_d10
        } else if (iconId == "02d") {
            R.drawable.big_d20
        } else if (iconId == "10d") {
            R.drawable.big_d01
        } else if (iconId == "01n") {
            R.drawable.big_n10
        } else if (iconId == "02n") {
            R.drawable.big_n20
        } else if (iconId == "10n") {
            R.drawable.big_n01
        } else {
            throw IllegalArgumentException()
        }

    fun getBackgroundWeatherId(iconId: String): Int =
        if (iconId == "03d" || iconId == "03n" || iconId == "04d" || iconId == "04n" || iconId == "50d" || iconId == "50n") {
            R.drawable.ic_background_clouds
        } else if (iconId == "09d" || iconId == "09n" || iconId == "11d" || iconId == "11n") {
            R.drawable.ic_background_shower_rain
        } else if (iconId == "13d" || iconId == "13n") {
            R.drawable.ic_background_snow
        } else if (iconId == "01d" || iconId == "02d" || iconId == "01n" || iconId == "02n") {
            R.drawable.ic_background_clear
        } else if (iconId == "10d" || iconId == "10n") {
            R.drawable.ic_background_rain
        } else {
            throw IllegalArgumentException()
        }

    fun getHourlyForecastIconId(iconId: String): Int =
        if (iconId == "03d" || iconId == "03n") {
            R.drawable.white_d30
        } else if (iconId == "04d" || iconId == "04n") {
            R.drawable.white_d40
        } else if (iconId == "09d" || iconId == "09n") {
            R.drawable.white_d90
        } else if (iconId == "11d" || iconId == "11n") {
            R.drawable.white_d11
        } else if (iconId == "13d" || iconId == "13n") {
            R.drawable.white_d31
        } else if (iconId == "50d" || iconId == "50n") {
            R.drawable.white_d05
        } else if (iconId == "01d") {
            R.drawable.white_d10
        } else if (iconId == "02d") {
            R.drawable.white_d20
        } else if (iconId == "10d") {
            R.drawable.white_d01
        } else if (iconId == "01n") {
            R.drawable.white_n10
        } else if (iconId == "02n") {
            R.drawable.white_n20
        } else if (iconId == "10n") {
            R.drawable.white_n01
        } else {
            throw IllegalArgumentException()
        }

    fun getSpeedTitle(speed: Int): String = app.getString(R.string.m_sec, speed.toString())

    fun getRainStr(rain: Int): String? =
        if (rain != 0) app.getString(R.string.millimeters_of_precipitation, rain.toString()) else null

    fun getSnowStr(snow: Int): String? =
        if (snow != 0) app.getString(R.string.millimeters_of_precipitation, snow.toString()) else null

    fun getPressureStr(pressure: Int, pressureType: String): String =
        if (pressureType == PressureType.TORR.id) app.getString(R.string.pressure_mm_hg, pressure.toString())
        else app.getString(R.string.pressure_hectopascal, pressure.toString())
}
package ru.neoanon.openweather.utils

import ru.neoanon.openweather.data.source.local.db.currentweather.CurrentWeather
import ru.neoanon.openweather.data.source.local.db.dailyforecast.DailyForecast
import ru.neoanon.openweather.data.source.local.db.hourlyforecast.HourlyForecast
import ru.neoanon.openweather.data.source.remote.model.DailyForecastApiItem
import ru.neoanon.openweather.data.source.remote.model.HourlyForecastApiItem
import ru.neoanon.openweather.data.source.remote.model.WeatherResponse
import ru.neoanon.openweather.model.enumerations.PressureType
import kotlin.math.round

/**
 *Created by eshtefan on  12.11.2018.
 */

private const val INTERVAL = 3//interval for hourly forecast. Do not modify, look at api response
private const val NUMBER_OF_DAY = 2// no more than 5 days(according to the OpenWeatherMap documentation)
private const val DAY = 24

fun CurrentWeather.Companion.fromOWM(response: WeatherResponse, pressureType: String, regionId: Long): CurrentWeather =
    CurrentWeather(
        regionId,
        response.weathers[0].icon,
        round(response.wind.speed).toInt(),
        getDirection(response.wind.deg),
        round(response.main.temp).toInt(),
        if (pressureType == PressureType.TORR.id) hectoPascalToTorr(response.main.pressure) else response.main.pressure.toInt(),
        response.main.humidity,
        response.weathers[0].description.capitalize()
    )

fun HourlyForecast.Companion.fromOWM(item: HourlyForecastApiItem, regionId: Long): HourlyForecast =
    HourlyForecast(regionId, item.timestamp * 1000, round(item.main.temp).toInt(), item.weathers[0].icon)

/**
 * Calculates the number of forecasts for today and the next day.
 *
 * @param currentHour hour of current time
 * @param timeOffset  offset of local time relative to UTC in hours
 * @return number of forecast
 */
fun HourlyForecast.Companion.getNumberOfHourlyForecast(currentHour: Int, timeOffset: Int): Int {
    val amount = DAY / INTERVAL//amount of forecasts for one day
    val nextDaysForecasts = amount * (NUMBER_OF_DAY - 1)
    var todayForecasts = 0
    for (i in 0 until DAY step INTERVAL) {
        val hour = (i + timeOffset + DAY) % DAY
        if (hour > currentHour) todayForecasts++
    }
    return todayForecasts + nextDaysForecasts
}

fun DailyForecast.Companion.fromOWM(item: DailyForecastApiItem, pressureType: String, regionId: Long): DailyForecast =
    DailyForecast(
        regionId,
        item.timestamp * 1000,
        arrayOf(item.temp.day, item.temp.morning, item.temp.evening, item.temp.night).average().toInt(),
        round(item.temp.day).toInt(),
        round(item.temp.night).toInt(),
        round(item.temp.evening).toInt(),
        round(item.temp.morning).toInt(),
        item.weathers[0].icon,
        item.weathers[0].description.capitalize(),
        round(item.speed).toInt(),
        getDirection(item.deg),
        if (pressureType == PressureType.TORR.id) hectoPascalToTorr(item.pressure) else item.pressure.toInt(),
        item.humidity,
        round(item.snow).toInt(),
        round(item.rain).toInt()
    )
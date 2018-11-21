package ru.neoanon.openweather.utils

import ru.neoanon.openweather.R
import ru.neoanon.openweather.data.source.local.db.currentweather.CurrentWeather
import ru.neoanon.openweather.data.source.local.db.dailyforecast.DailyForecast
import ru.neoanon.openweather.data.source.local.db.hourlyforecast.HourlyForecast
import ru.neoanon.openweather.model.*
import javax.inject.Inject

/**
 *Created by eshtefan on  13.11.2018.
 */

class ModelTransformer @Inject constructor(val dateHandler: DateHandler, val resProvider: ResourceProvider) {
    fun getDailyForecastItem(forecast: DailyForecast, pressureType: String): DailyForecastItem =
        DailyForecastItem(
            title = dateHandler.getDayOfMonthAndWeek(forecast.timestamp),
            morningTemp = buildTemperatureStr(forecast.morningTemp, null),
            dayTemp = buildTemperatureStr(forecast.dayTemp, null),
            eveningTemp = buildTemperatureStr(forecast.morningTemp, null),
            nightTemp = buildTemperatureStr(forecast.morningTemp, null),
            weatherIconId = resProvider.getDailyForecastIconId(forecast.iconId),
            weatherDescription = forecast.description,
            windDirectIconId = resProvider.getMiddleWindIconId(forecast.windDirect),
            windDescription = String.format(
                "%s, %s",
                resProvider.getSpeedTitle(forecast.windSpeed),
                resProvider.getLocalizedWindDirection(forecast.windDirect)
            ),
            snow = resProvider.getSnowStr(forecast.snow),
            rain = resProvider.getRainStr(forecast.rain),
            isWithoutPrecipitation = (forecast.snow == 0 && forecast.rain == 0),
            pressure = resProvider.getPressureStr(forecast.pressure, pressureType),
            humidity = String.format("%s %%", forecast.humidity.toString())
        )

    fun getWeatherItem(weather: CurrentWeather): CurrentWeatherItem =
        CurrentWeatherItem(
            temp = buildTemperatureStr(weather.temp, null),
            iconId = resProvider.getCurrentWeatherIconId(weather.iconId),
            weatherDescription = weather.description,
            backgroundIconId = resProvider.getBackgroundWeatherId(weather.iconId)
        )

    fun getHeaderHourlyForecastItem(weather: CurrentWeather, pressureType: String): CommonHourlyItem =
        HeaderHourlyForecastItem(
            windDirectIconId = resProvider.getWindIconId(weather.windDirect),
            windDescription = String.format(
                "%s, %s",
                resProvider.getSpeedTitle(weather.windSpeed),
                resProvider.getLocalizedWindDirection(weather.windDirect)
            ),
            pressure = resProvider.getPressureStr(weather.pressure, pressureType),
            humidity = String.format("%s %%", weather.humidity.toString())
        )

    fun getFirstHourlyForecastItem(weather: CurrentWeather, tempType: String): CommonHourlyItem =
        HourlyForecastItem(
            time = dateHandler.getHoursAndMinutes(dateHandler.getCurrentTimestamp()),
            temp = buildTemperatureStr(weather.temp, tempType),
            iconId = resProvider.getHourlyForecastIconId(weather.iconId)
        )

    fun getHourlyForecastItem(forecast: HourlyForecast, tempType: String): CommonHourlyItem =
        HourlyForecastItem(
            time = dateHandler.getHoursAndMinutes(forecast.timestamp),
            temp = buildTemperatureStr(forecast.temp, tempType),
            iconId = resProvider.getHourlyForecastIconId(forecast.iconId)
        )

    fun getDailyShortItem(forecast: DailyForecast, tempType: String): DailyForecastShortItem {
        val dateInfo = dateHandler.getDataInfo(forecast.timestamp)
        return DailyForecastShortItem(
            dayOfWeek = dateInfo.dayOfWeek,
            dayOfMonth = dateInfo.dayOfMonth,
            temp = buildTemperatureStr(forecast.averageTemp, tempType),
            iconId = resProvider.getDailyForecastIconId(forecast.iconId),
            dayOfWeekColorId = if (dateInfo.isWeekend) R.color.colorWeekend else R.color.colorWeekday
        )
    }
}
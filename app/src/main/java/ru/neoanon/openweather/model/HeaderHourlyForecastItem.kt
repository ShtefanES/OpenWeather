package ru.neoanon.openweather.model

/**
 *Created by eshtefan on  13.11.2018.
 */

data class HeaderHourlyForecastItem(
    val windDirectIconId: Int,
    val windDescription: String,
    val pressure: String,
    val humidity: String
) : CommonHourlyItem
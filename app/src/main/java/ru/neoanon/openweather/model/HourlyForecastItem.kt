package ru.neoanon.openweather.model

/**
 *Created by eshtefan on  13.11.2018.
 */

data class HourlyForecastItem(
    val time: String,
    val temp: String,
    val iconId: Int
) : CommonHourlyItem

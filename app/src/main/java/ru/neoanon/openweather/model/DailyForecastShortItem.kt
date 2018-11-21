package ru.neoanon.openweather.model

/**
 *Created by eshtefan on  12.11.2018.
 */

data class DailyForecastShortItem(
    val dayOfWeek: String,
    val dayOfMonth: String,
    val temp: String,
    val iconId: Int,
    val dayOfWeekColorId: Int
)
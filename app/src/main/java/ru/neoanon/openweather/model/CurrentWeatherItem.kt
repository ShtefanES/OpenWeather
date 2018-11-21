package ru.neoanon.openweather.model

/**
 *Created by eshtefan on  12.11.2018.
 */

data class CurrentWeatherItem(
    val temp: String,
    val iconId: Int,
    val weatherDescription: String,
    val backgroundIconId: Int
)
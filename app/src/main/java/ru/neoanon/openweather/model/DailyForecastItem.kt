package ru.neoanon.openweather.model

import java.io.Serializable

/**
 *Created by eshtefan on  12.11.2018.
 */


data class DailyForecastItem(
    val title: String,
    val morningTemp: String,
    val dayTemp: String,
    val eveningTemp: String,
    val nightTemp: String,
    val weatherIconId: Int,
    val weatherDescription: String,
    val windDirectIconId: Int,
    val windDescription: String,
    val snow: String?,
    val rain: String?,
    val isWithoutPrecipitation: Boolean,
    val pressure: String,
    val humidity: String
) : Serializable
package ru.neoanon.openweather.data.source.remote.model

import com.google.gson.annotations.SerializedName

/**
 *Created by eshtefan on  08.11.2018.
 */

data class WeatherResponse(
    @SerializedName("weather")
    val weathers: List<Weather>,
    val main: Main,
    val wind: Wind
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val pressure: Double,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Double
)

data class DailyForecastResponse(
    @SerializedName("cnt")
    val amount: Int,
    val list: List<DailyForecastApiItem>
)

data class DailyForecastApiItem(
    @SerializedName("dt")
    val timestamp: Long,
    val temp: Temperature,
    val pressure: Double,
    val humidity: Int,
    @SerializedName("weather")
    val weathers: List<Weather>,
    val speed: Double,
    val deg: Double,
    val snow: Double,
    val rain: Double
)

data class Temperature(
    val day: Double,
    val night: Double,
    @SerializedName("eve")
    val evening: Double,
    @SerializedName("morn")
    val morning: Double
)

data class HourlyForecastResponse(
    @SerializedName("cnt")
    val amount: Int,
    val list: List<HourlyForecastApiItem>
)

data class HourlyForecastApiItem(
    @SerializedName("dt")
    val timestamp: Long,
    val main: Main,
    @SerializedName("weather")
    val weathers: List<Weather>
)
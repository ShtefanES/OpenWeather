package ru.neoanon.openweather.data.source.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.neoanon.openweather.data.source.remote.model.DailyForecastResponse
import ru.neoanon.openweather.data.source.remote.model.HourlyForecastResponse
import ru.neoanon.openweather.data.source.remote.model.WeatherResponse

/**
 *Created by eshtefan on  08.11.2018.
 */

interface OpenWeatherMap {
    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String,
        @Query("type") type: String,
        @Query("lang") lang: String
    ): Observable<WeatherResponse>

    @GET("data/2.5/forecast")
    fun getHourlyForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String,
        @Query("type") type: String,
        @Query("lang") lang: String,
        @Query("cnt") cnt: Int
    ): Observable<HourlyForecastResponse>

    @GET("data/2.5/forecast/daily")
    fun getDailyForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String,
        @Query("type") type: String,
        @Query("lang") lang: String,
        @Query("cnt") cnt: Int
    ): Observable<DailyForecastResponse>
}
package ru.neoanon.openweather.data.source.local.db

import io.reactivex.Flowable
import ru.neoanon.openweather.data.source.local.db.currentweather.CurrentWeather
import ru.neoanon.openweather.data.source.local.db.dailyforecast.DailyForecast
import ru.neoanon.openweather.data.source.local.db.hourlyforecast.HourlyForecast
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation

/**
 *Created by eshtefan on  11.11.2018.
 */

interface IDbSource {
    fun saveLocation(regionLocation: RegionLocation): Long

    fun getLocation(id: Long): RegionLocation

    fun getLocation(locationName: String): RegionLocation

    fun loadAllLocations(): Flowable<List<RegionLocation>>

    fun loadAllDailyForecastForRegion(regionId: Long): Flowable<List<DailyForecast>>

    fun loadAllHourlyForecastForRegion(regionId: Long): Flowable<List<HourlyForecast>>

    fun loadWeatherById(regionId: Long): Flowable<List<CurrentWeather>>

    fun loadAllDailyForecasts(regionId: Long): List<DailyForecast>

    fun deleteLocation(id: Long)

    fun saveWeather(weather: CurrentWeather): Long

    fun getWeather(regionId: Long): CurrentWeather

    fun deleteAllWeathers()

    fun deleteAllHourlyForecasts()

    fun deleteAllDailyForecasts()

    fun deleteWeather(regionId: Long): Int

    fun saveHourlyForecasts(forecasts: List<HourlyForecast>)

    fun deleteHourlyForecasts(regionId: Long): Int

    fun saveDailyForecasts(forecasts: List<DailyForecast>)

    fun deleteDailyForecasts(regionId: Long): Int
}
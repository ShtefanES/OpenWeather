package ru.neoanon.openweather.data.source.local.db

import io.reactivex.Flowable
import ru.neoanon.openweather.data.source.local.db.currentweather.CurrentWeather
import ru.neoanon.openweather.data.source.local.db.dailyforecast.DailyForecast
import ru.neoanon.openweather.data.source.local.db.hourlyforecast.HourlyForecast
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation
import javax.inject.Inject

/**
 *Created by eshtefan on  11.11.2018.
 */

class DbSource @Inject constructor(val appDatabase: AppDatabase) : IDbSource {

    override fun saveLocation(regionLocation: RegionLocation): Long =
        appDatabase.locationDao().insertLocation(regionLocation)

    override fun getLocation(id: Long): RegionLocation =
        appDatabase.locationDao().loadLocation(id)

    override fun getLocation(locationName: String): RegionLocation =
        appDatabase.locationDao().loadLocation(locationName)

    override fun loadAllLocations(): Flowable<List<RegionLocation>> =
        appDatabase.locationDao().loadAllLocations()

    override fun loadAllDailyForecastForRegion(regionId: Long): Flowable<List<DailyForecast>> =
        appDatabase.dailyForecastDao().loadAllForecastById(regionId)

    override fun loadAllHourlyForecastForRegion(regionId: Long): Flowable<List<HourlyForecast>> =
        appDatabase.hourlyForecastDao().loadAllForecastById(regionId)

    override fun loadWeatherById(regionId: Long): Flowable<List<CurrentWeather>> =
        appDatabase.currentWeatherDao().loadWeatherById(regionId)

    override fun loadAllDailyForecasts(regionId: Long): List<DailyForecast> =
        appDatabase.dailyForecastDao().loadForecasts(regionId)

    override fun deleteLocation(id: Long) {
        appDatabase.locationDao().deleteRegionById(id)
    }

    override fun saveWeather(weather: CurrentWeather): Long =
        appDatabase.currentWeatherDao().insertWeather(weather)

    override fun getWeather(regionId: Long): CurrentWeather =
        appDatabase.currentWeatherDao().loadWeather(regionId)

    override fun deleteAllWeathers() {
        appDatabase.currentWeatherDao().clearAllWeathers()
    }

    override fun deleteAllHourlyForecasts() {
        appDatabase.hourlyForecastDao().clearAllForecasts()
    }

    override fun deleteAllDailyForecasts() {
        appDatabase.dailyForecastDao().clearAllForecasts()
    }

    override fun deleteWeather(regionId: Long): Int =
        appDatabase.currentWeatherDao().deleteWeatherById(regionId)


    override fun saveHourlyForecasts(forecasts: List<HourlyForecast>) =
        appDatabase.hourlyForecastDao().insertForecast(forecasts)


    override fun deleteHourlyForecasts(regionId: Long): Int =
        appDatabase.hourlyForecastDao().deleteForecastsById(regionId)


    override fun saveDailyForecasts(forecasts: List<DailyForecast>) {
        appDatabase.dailyForecastDao().insertForecast(forecasts)
    }

    override fun deleteDailyForecasts(regionId: Long): Int =
        appDatabase.dailyForecastDao().deleteForecastsById(regionId)
}
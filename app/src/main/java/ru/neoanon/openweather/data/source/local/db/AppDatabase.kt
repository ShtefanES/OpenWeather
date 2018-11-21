package ru.neoanon.openweather.data.source.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.neoanon.openweather.data.source.local.db.currentweather.CurrentWeather
import ru.neoanon.openweather.data.source.local.db.currentweather.CurrentWeatherDao
import ru.neoanon.openweather.data.source.local.db.dailyforecast.DailyForecast
import ru.neoanon.openweather.data.source.local.db.dailyforecast.DailyForecastDao
import ru.neoanon.openweather.data.source.local.db.hourlyforecast.HourlyForecast
import ru.neoanon.openweather.data.source.local.db.hourlyforecast.HourlyForecastDao
import ru.neoanon.openweather.data.source.local.db.location.LocationDao
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation

/**
 *Created by eshtefan on  09.11.2018.
 */

@Database(
    entities = arrayOf(
        CurrentWeather::class,
        HourlyForecast::class,
        DailyForecast::class,
        RegionLocation::class
    ), version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun hourlyForecastDao(): HourlyForecastDao

    abstract fun dailyForecastDao(): DailyForecastDao

    abstract fun locationDao(): LocationDao
}
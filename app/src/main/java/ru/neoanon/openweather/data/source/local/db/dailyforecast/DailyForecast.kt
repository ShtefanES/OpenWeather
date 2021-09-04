package ru.neoanon.openweather.data.source.local.db.dailyforecast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 *Created by eshtefan on  11.11.2018.
 */

@Entity(tableName = "daily_forecast")
data class DailyForecast(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "region_id")
    var regionId: Long,
    var timestamp: Long,
    @ColumnInfo(name = "temp_average")
    var averageTemp: Int,
    @ColumnInfo(name = "temp_day")
    var dayTemp: Int,
    @ColumnInfo(name = "temp_night")
    var nightTemp: Int,
    @ColumnInfo(name = "temp_evening")
    var eveningTemp: Int,
    @ColumnInfo(name = "temp_morning")
    var morningTemp: Int,
    var iconId: String,
    var description: String,
    @ColumnInfo(name = "wind_speed")
    var windSpeed: Int,
    @ColumnInfo(name = "wind_direct")
    var windDirect: String,
    var pressure: Int,
    var humidity: Int,
    var snow: Int,
    var rain: Int
) {
    @Ignore
    constructor(
        regionId: Long,
        timestamp: Long,
        averageTemp: Int,
        dayTemp: Int,
        nightTemp: Int,
        eveningTemp: Int,
        morningTemp: Int,
        iconId: String,
        description: String,
        windSpeed: Int,
        windDirect: String,
        pressure: Int,
        humidity: Int,
        snow: Int,
        rain: Int
    ) : this(
        0,
        regionId,
        timestamp,
        averageTemp,
        dayTemp,
        nightTemp,
        eveningTemp,
        morningTemp,
        iconId,
        description,
        windSpeed,
        windDirect,
        pressure,
        humidity,
        snow,
        rain
    )

    companion object {}
}
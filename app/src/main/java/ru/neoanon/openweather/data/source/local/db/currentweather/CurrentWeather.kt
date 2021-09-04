package ru.neoanon.openweather.data.source.local.db.currentweather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by eshtefan on  08.11.2018.
 */

@Entity(tableName = "current_weather")
data class CurrentWeather(
    @PrimaryKey
    @ColumnInfo(name = "region_id")
    var regionId: Long,
    @ColumnInfo(name = "icon_id")
    var iconId: String,
    @ColumnInfo(name = "wind_speed")
    var windSpeed: Int,
    @ColumnInfo(name = "wind_direct")
    var windDirect: String,
    var temp: Int,
    var pressure: Int,
    var humidity: Int,
    var description: String
) {
    companion object {}
}


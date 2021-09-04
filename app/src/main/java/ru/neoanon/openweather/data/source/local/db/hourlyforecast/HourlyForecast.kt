package ru.neoanon.openweather.data.source.local.db.hourlyforecast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 *Created by eshtefan on  11.11.2018.
 */
@Entity(tableName = "hourly_forecast")
data class HourlyForecast(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "region_id")
    var regionId: Long,
    var timestamp: Long,
    var temp: Int,
    @ColumnInfo(name = "icon_id")
    var iconId: String
) {
    @Ignore
    constructor(regionId: Long, timestamp: Long, temp: Int, iconId: String) : this(0, regionId, timestamp, temp, iconId)

    companion object {}
}
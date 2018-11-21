package ru.neoanon.openweather.data.source.local.db.hourlyforecast

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

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
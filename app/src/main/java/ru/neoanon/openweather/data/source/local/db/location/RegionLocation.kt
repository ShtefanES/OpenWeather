package ru.neoanon.openweather.data.source.local.db.location

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import ru.neoanon.openweather.model.LocationDto

/**
 *Created by eshtefan on  11.11.2018.
 */
@Entity(tableName = "region_location")
data class RegionLocation(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var name: String,
    var latitude: Double,
    var longitude: Double
) {
    constructor(location: LocationDto, name: String) : this(0, name, location.latitude, location.longitude)
}
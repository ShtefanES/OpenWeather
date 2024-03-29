package ru.neoanon.openweather.data.source.local.db.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

/**
 *Created by eshtefan on  11.11.2018.
 */
@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(regionLocation: RegionLocation): Long

    @Query("SELECT * FROM region_location WHERE id=:id")
    fun loadLocation(id: Long): RegionLocation

    @Query("SELECT * FROM region_location WHERE name=:locationName")
    fun loadLocation(locationName: String): RegionLocation

    @Query("DELETE FROM region_location")
    fun clearAllLocations()

    @Query("DELETE FROM region_location WHERE id=:id")
    fun deleteRegionById(id: Long): Int

    @Query("SELECT * FROM region_location ORDER BY id ASC")
    fun loadAllLocations(): Flowable<List<RegionLocation>>
}
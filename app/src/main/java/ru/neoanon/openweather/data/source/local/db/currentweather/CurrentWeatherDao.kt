package ru.neoanon.openweather.data.source.local.db.currentweather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

/**
 *Created by eshtefan on  09.11.2018.
 */

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(currentWeather: CurrentWeather): Long

    @Query("SELECT * FROM current_weather WHERE region_id=:regionId")
    fun loadWeather(regionId: Long): CurrentWeather

    @Query("DELETE FROM current_weather")
    fun clearAllWeathers()

    @Query("DELETE FROM current_weather WHERE region_id=:regionId")
    fun deleteWeatherById(regionId: Long): Int

    @Query("SELECT * FROM current_weather WHERE region_id=:regionId")
    fun loadWeatherById(regionId: Long): Flowable<List<CurrentWeather>>
}
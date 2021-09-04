package ru.neoanon.openweather.data.source.local.db.hourlyforecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

/**
 *Created by eshtefan on  11.11.2018.
 */

@Dao
interface HourlyForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecasts: List<HourlyForecast>)

    @Query("SELECT * FROM hourly_forecast WHERE region_id=:regionId")
    fun loadForecasts(regionId: Long): List<HourlyForecast>

    @Query("DELETE FROM hourly_forecast")
    fun clearAllForecasts()

    @Query("DELETE FROM hourly_forecast WHERE region_id=:regionId")
    fun deleteForecastsById(regionId: Long): Int

    @Query("SELECT * FROM hourly_forecast WHERE region_id=:regionId")
    fun loadAllForecastById(regionId: Long): Flowable<List<HourlyForecast>>
}
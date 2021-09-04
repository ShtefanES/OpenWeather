package ru.neoanon.openweather.data.source.local.db.dailyforecast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

/**
 *Created by eshtefan on  11.11.2018.
 */

@Dao
interface DailyForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecasts: List<DailyForecast>)

    @Query("SELECT * FROM daily_forecast WHERE region_id=:regionId")
    fun loadForecasts(regionId: Long): List<DailyForecast>

    @Query("DELETE FROM daily_forecast")
    fun clearAllForecasts()

    @Query("DELETE FROM daily_forecast WHERE region_id=:regionId")
    fun deleteForecastsById(regionId: Long): Int

    @Query("SELECT * FROM daily_forecast WHERE region_id=:regionId")
    fun loadAllForecastById(regionId: Long): Flowable<List<DailyForecast>>
}
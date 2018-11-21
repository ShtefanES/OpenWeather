package ru.neoanon.openweather.data.source

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation
import ru.neoanon.openweather.model.*

/**
 *Created by eshtefan on  12.11.2018.
 */

interface IForecastsRepo {

    fun getRegionLocation(regionId: Long): Observable<RegionLocation>

    fun getRegionLocation(regionName: String): RegionLocation?

    fun addRegionLocation(regionLocation: RegionLocation): Long

    fun updateData(regionLocation: RegionLocation): Observable<RegionLocation>

    fun getAllRegionLocations(): Flowable<List<RegionLocation>>

    fun deleteAllInfoForThisRegion(id: Long): Completable

    fun getAllShortDailyForecasts(regionId: Long): Flowable<List<DailyForecastShortItem>>

    fun getAllCommonHourlyForecasts(regionId: Long): Flowable<List<CommonHourlyItem>>

    fun getCurrentWeather(regionId: Long): Flowable<CurrentWeatherItem>

    fun getAllDailyForecasts(regionId: Long): Observable<List<DailyForecastItem>>

    fun getUnits(): Observable<UnitsOfWeather>

    fun setUnits(unit: UnitsOfWeather)

    fun deleteAllForecasts()

    fun makeCacheDirty()
}
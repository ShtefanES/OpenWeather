package ru.neoanon.openweather.data.source.geodata

import io.reactivex.Observable
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation

/**
 *Created by eshtefan on  12.11.2018.
 */

interface IRegionSource {

    fun getCurrentRegionLocation(): Observable<RegionLocation>

    fun getAddressSuggestions(userRequest: String): Observable<List<RegionLocation>>
}
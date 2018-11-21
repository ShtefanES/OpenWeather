package ru.neoanon.openweather.data.source.geodata

import io.reactivex.Observable
import ru.neoanon.openweather.model.LocationDto

/**
 *Created by eshtefan on  12.11.2018.
 */

interface ILocationProvider {

    fun getLastKnownLocation(): Observable<LocationDto>
}
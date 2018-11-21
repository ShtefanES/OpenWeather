package ru.neoanon.openweather.data.source.geodata

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import ru.neoanon.openweather.model.LocationDto
import timber.log.Timber

/**
 *Created by eshtefan on  12.11.2018.
 */

class LocationProvider(val locationProvider: ReactiveLocationProvider) : ILocationProvider {
    override fun getLastKnownLocation(): Observable<LocationDto> =
        this.locationProvider.lastKnownLocation
            .flatMap { location ->
                Timber.d(String.format(" LastKnownLocation location: %s,%s", location.latitude, location.longitude))
                Observable.just(LocationDto(location.latitude, location.longitude))
                    .subscribeOn(Schedulers.io())
            }

}
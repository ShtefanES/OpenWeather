package ru.neoanon.openweather.data.source.geodata

import android.app.Application
import android.location.Address
import android.location.Geocoder
import io.reactivex.Observable
import ru.neoanon.openweather.R
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation
import ru.neoanon.openweather.model.LocationDto

/**
 *Created by eshtefan on  12.11.2018.
 */

class RegionSource(val iLocationProvider: ILocationProvider, val geocoder: Geocoder, val application: Application) :
    IRegionSource {

    override fun getCurrentRegionLocation(): Observable<RegionLocation> {
        return iLocationProvider.getLastKnownLocation()
            .map { locationDto ->
                val address: List<Address> = geocoder.getFromLocation(locationDto.latitude, locationDto.longitude, 2)
                val regionName = if (address.isNotEmpty()) {
                    if (address[0].locality != null) address[0].locality else if (address[0].subAdminArea != null) address[0].subAdminArea else application.applicationContext.getString(
                        R.string.nameless_location
                    )
                } else {
                    application.applicationContext.getString(R.string.nameless_location)
                }
                RegionLocation(locationDto, regionName)
            }
    }

    override fun getAddressSuggestions(userRequest: String): Observable<List<RegionLocation>> =
        Observable.fromCallable {
            val address: List<Address> = geocoder.getFromLocationName(userRequest, 5)
            val regionLocations = arrayListOf<RegionLocation>()
            for (addr in address) {
                val locationName: String? = if (addr.locality != null) addr.locality else addr.subAdminArea
                if (locationName != null) {
                    val regionLocation = RegionLocation(LocationDto(addr.latitude, addr.longitude), locationName)
                    regionLocations.add(regionLocation)
                }
            }
            regionLocations
        }
}

package ru.neoanon.openweather.data.source.geodata

import android.app.Application
import android.content.Context
import android.location.Address
import android.location.Geocoder
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import ru.neoanon.openweather.model.LocationDto
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import ru.neoanon.openweather.R
import ru.neoanon.openweather.RxTestRule

/**
 * Created by eshtefan on  19.11.2018.
 */

class RegionSourceTest {
    @JvmField
    @Rule
    val mockitoRule = MockitoJUnit.rule()
    @JvmField
    @Rule
    val rxTestRule = RxTestRule()

    @Mock
    lateinit var iLocationProvider: ILocationProvider
    @Mock
    lateinit var geocoder: Geocoder
    @Mock
    lateinit var application: Application
    @Mock
    lateinit var context: Context
    @Mock
    lateinit var addressWithLocality: Address
    @Mock
    lateinit var addressWithSubAdminArea: Address

    private lateinit var iRegionSource: IRegionSource
    private lateinit var locationDto: LocationDto
    private val latitude = 10.4
    private val longitude = 14.7
    private val locality = "Moscow"
    private val subAdminArea = "Novosibirsk region"
    private val userRequest = "Novosib"

    @Before
    fun setUp() {
        locationDto = LocationDto(latitude, longitude)

        iRegionSource = RegionSource(iLocationProvider, geocoder, application)
    }

    @Test
    fun getRegionLocationWhenGeocoderReturnEmptyList() {
        val namelessStr = "nameless"
        whenever(iLocationProvider.getLastKnownLocation()).then { Observable.just(locationDto) }
        whenever(geocoder.getFromLocation(latitude, longitude, 2)).then { listOf<Address>() }
        whenever(application.applicationContext).then { context }
        whenever(context.getString(R.string.nameless_location)).then { namelessStr }

        iRegionSource.getCurrentRegionLocation()
            .test()
            .assertValue { it.name == namelessStr && it.latitude == latitude && it.longitude == longitude }
            .assertNoErrors()
    }

    @Test
    fun getRegionLocationWhenGeocoderReturnLocality() {
        whenever(iLocationProvider.getLastKnownLocation()).then { Observable.just(locationDto) }
        whenever(geocoder.getFromLocation(latitude, longitude, 2)).then { listOf(addressWithLocality) }
        whenever(addressWithLocality.locality).then { locality }

        iRegionSource.getCurrentRegionLocation()
            .test()
            .assertValue { it.name == locality && it.latitude == latitude && it.longitude == longitude }
            .assertNoErrors()
    }

    @Test
    fun getRegionLocationWhenGeocoderReturnSubAdminArea() {
        whenever(iLocationProvider.getLastKnownLocation()).then { Observable.just(locationDto) }
        whenever(geocoder.getFromLocation(latitude, longitude, 2)).then { listOf(addressWithSubAdminArea) }
        whenever(addressWithSubAdminArea.subAdminArea).then { subAdminArea }

        iRegionSource.getCurrentRegionLocation()
            .test()
            .assertValue { it.name == subAdminArea && it.latitude == latitude && it.longitude == longitude }
            .assertNoErrors()
    }

    @Test
    fun getAddressSuggestionsWhenGeocoderReturnEmptyList() {
        whenever(geocoder.getFromLocationName(userRequest, 5)).then { listOf<Address>() }
        iRegionSource.getAddressSuggestions(userRequest)
            .test()
            .assertValue { it.isEmpty() }
    }

    @Test
    fun getAddressSuggestionsWhenGeocoderReturnLocality() {
        whenever(geocoder.getFromLocationName(userRequest, 5)).then { listOf(addressWithLocality) }
        whenever(addressWithLocality.locality).then { locality }
        whenever(addressWithLocality.latitude).then { latitude }
        whenever(addressWithLocality.longitude).then { longitude }
        iRegionSource.getAddressSuggestions(userRequest)
            .test()
            .assertValue { it[0].name == locality && it[0].latitude == latitude && it[0].longitude == longitude }
    }

    @Test
    fun getAddressSuggestionsWhenGeocoderReturnSubAdminArea() {
        whenever(geocoder.getFromLocationName(userRequest, 5)).then { listOf(addressWithSubAdminArea) }
        whenever(addressWithSubAdminArea.locality).then { subAdminArea }
        whenever(addressWithSubAdminArea.latitude).then { latitude }
        whenever(addressWithSubAdminArea.longitude).then { longitude }
        iRegionSource.getAddressSuggestions(userRequest)
            .test()
            .assertValue { it[0].name == subAdminArea && it[0].latitude == latitude && it[0].longitude == longitude }
    }
}
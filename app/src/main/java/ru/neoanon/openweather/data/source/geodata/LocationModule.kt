package ru.neoanon.openweather.data.source.geodata

import android.app.Application
import android.content.Context
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider
import java.util.*
import javax.inject.Singleton

/**
 *Created by eshtefan on  12.11.2018.
 */

@Module
class LocationModule {
    @Singleton
    @Provides
    fun providesReactiveLocationProvider(appContext: Context): ReactiveLocationProvider =
        ReactiveLocationProvider(appContext)

    @Singleton
    @Provides
    fun providesGeocoder(appContext: Context, locale: Locale): Geocoder = Geocoder(appContext, locale)

    @Singleton
    @Provides
    fun providesLocationProvider(reactiveLocationProvider: ReactiveLocationProvider): ILocationProvider =
        LocationProvider(reactiveLocationProvider)

    @Singleton
    @Provides
    fun providesLocationSource(
        iLocationProvider: ILocationProvider,
        geocoder: Geocoder,
        application: Application
    ): IRegionSource = RegionSource(iLocationProvider, geocoder, application)
}
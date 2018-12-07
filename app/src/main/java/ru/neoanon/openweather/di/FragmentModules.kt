package ru.neoanon.openweather.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.neoanon.openweather.view.detailedforecast.ForecastPagerFragment
import ru.neoanon.openweather.view.mainscreen.MainFragment
import ru.neoanon.openweather.view.places.PlacesFragment

/**
 *Created by eshtefan on  06.12.2018.
 */

@Module
interface FragmentModules {
    @ContributesAndroidInjector
    fun contributesMainFragment(): MainFragment

    @ContributesAndroidInjector
    fun contributesForecastPagerFragment(): ForecastPagerFragment

    @ContributesAndroidInjector
    fun contributesPlacesFragment(): PlacesFragment
}
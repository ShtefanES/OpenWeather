package ru.neoanon.openweather.app

import dagger.Component
import ru.neoanon.openweather.data.source.RepoModule
import ru.neoanon.openweather.data.source.geodata.LocationModule
import ru.neoanon.openweather.data.source.local.db.DbModule
import ru.neoanon.openweather.data.source.local.preference.SettingsModule
import ru.neoanon.openweather.data.source.remote.OwmApiModule
import ru.neoanon.openweather.utils.LocaleModule
import ru.neoanon.openweather.view.detailedforecast.ForecastPagerFragment
import ru.neoanon.openweather.view.mainscreen.MainActivity
import ru.neoanon.openweather.view.mainscreen.MainFragment
import ru.neoanon.openweather.view.places.PlacesFragment
import ru.neoanon.openweather.view.settings.SettingsActivity
import javax.inject.Singleton

/**
 *Created by eshtefan on  08.11.2018.
 */

@Singleton
@Component(modules = [AppModule::class, OwmApiModule::class, DbModule::class, SettingsModule::class, LocationModule::class, LocaleModule::class, RepoModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(placesFragment: PlacesFragment)

    fun inject(mainFragment: MainFragment)

    fun inject(forecastPagerFragment: ForecastPagerFragment)

    fun inject(settingsActivity: SettingsActivity)
}
package ru.neoanon.openweather.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.neoanon.openweather.view.mainscreen.MainActivity

/**
 *Created by eshtefan on  06.12.2018.
 */

@Module
interface MainModules {
    @ContributesAndroidInjector(modules = [WeatherPresentationModule::class])
    fun contributesMainActivity(): MainActivity
}
package ru.neoanon.openweather.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.neoanon.openweather.view.mainscreen.MainActivity
import ru.neoanon.openweather.view.settings.SettingsActivity

/**
 *Created by eshtefan on  06.12.2018.
 */

@Module
interface ActivityModules {
    @ContributesAndroidInjector
    fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun contributesSettingsActivity(): SettingsActivity
}
package ru.neoanon.openweather.data.source.local.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *Created by eshtefan on  11.11.2018.
 */

@Module
class SettingsModule {
    private val MAIN_PREFERENCES = "mainPreferences"

    @Singleton
    @Provides
    fun providesSettings(appContext: Context): ISettings =
        Settings(appContext.getSharedPreferences(MAIN_PREFERENCES, MODE_PRIVATE))
}
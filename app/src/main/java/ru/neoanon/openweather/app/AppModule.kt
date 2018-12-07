package ru.neoanon.openweather.app

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *Created by eshtefan on  08.11.2018.
 */

@Module
class AppModule {
    @Provides
    @Singleton
    fun providesContext(app: Application): Context = app.applicationContext
}
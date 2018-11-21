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
class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application = app

    @Provides
    @Singleton
    fun providesContext(): Context = app.applicationContext
}
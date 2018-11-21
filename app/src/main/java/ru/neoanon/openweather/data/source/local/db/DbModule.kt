package ru.neoanon.openweather.data.source.local.db

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *Created by eshtefan on  11.11.2018.
 */

@Module
class DbModule {

    @Singleton
    @Provides
    fun providesDbSource(appContext: Context): IDbSource {
        return DbSource(
            Room.databaseBuilder(appContext, AppDatabase::class.java, "weather-database")
                .fallbackToDestructiveMigration().build()
        )
    }
}
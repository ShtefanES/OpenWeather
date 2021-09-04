package ru.neoanon.openweather.data.source.local.db

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *Created by eshtefan on  11.11.2018.
 */

@Module
abstract class DbModule {

	companion object {

		@Singleton
		@Provides
		fun providesAppDatabase(appContext: Context): AppDatabase =
			Room.databaseBuilder(appContext, AppDatabase::class.java, "weather-database")
				.fallbackToDestructiveMigration().build()
	}

	@Singleton
	@Binds
	abstract fun providesDbSource(source: DbSource): IDbSource
}
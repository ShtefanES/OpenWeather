package ru.neoanon.openweather.data.source

import dagger.Module
import dagger.Provides
import ru.neoanon.openweather.data.source.local.db.IDbSource
import ru.neoanon.openweather.data.source.local.preference.ISettings
import ru.neoanon.openweather.data.source.remote.OpenWeatherMap
import ru.neoanon.openweather.utils.DateHandler
import ru.neoanon.openweather.utils.ModelTransformer
import java.util.*
import javax.inject.Singleton

/**
 *Created by eshtefan on  14.11.2018.
 */

@Module
class RepoModule {

    @Singleton
    @Provides
    fun providesRepo(
        owm: OpenWeatherMap,
        iDbSource: IDbSource,
        iSettings: ISettings,
        modelTransformer: ModelTransformer,
        dateHandler: DateHandler,
        locale: Locale
    ): IForecastsRepo = ForecastsRepo(
        owm,
        iDbSource,
        iSettings,
        modelTransformer,
        dateHandler,
        locale
    )
}
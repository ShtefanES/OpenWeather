package ru.neoanon.openweather.utils

import dagger.Module
import dagger.Provides
import ru.neoanon.openweather.model.enumerations.Lang
import java.util.Locale
import javax.inject.Singleton

/**
 *Created by eshtefan on  13.11.2018.
 */

@Module
class LocaleModule {
    @Singleton
    @Provides
    fun providesLocale(): Locale =
        if (Locale.getDefault().language == Lang.RU.id) Locale(Lang.RU.id) else Locale(Lang.EN.id)

}
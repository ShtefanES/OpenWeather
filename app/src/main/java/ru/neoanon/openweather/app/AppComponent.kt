package ru.neoanon.openweather.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import ru.neoanon.openweather.data.source.RepoModule
import ru.neoanon.openweather.data.source.geodata.LocationModule
import ru.neoanon.openweather.data.source.local.db.DbModule
import ru.neoanon.openweather.data.source.local.preference.SettingsModule
import ru.neoanon.openweather.data.source.remote.OwmApiModule
import ru.neoanon.openweather.di.ActivityModules
import ru.neoanon.openweather.di.FragmentModules
import ru.neoanon.openweather.utils.LocaleModule
import javax.inject.Singleton

/**
 *Created by eshtefan on  08.11.2018.
 */

@Singleton
@Component(modules = [AppModule::class, OwmApiModule::class, DbModule::class, SettingsModule::class, LocationModule::class,
    LocaleModule::class, RepoModule::class, AndroidSupportInjectionModule::class, ActivityModules::class, FragmentModules::class])
interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
    fun inject(app: App)
}
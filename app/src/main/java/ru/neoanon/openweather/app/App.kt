package ru.neoanon.openweather.app

import android.app.Application
import ru.neoanon.openweather.BuildConfig
import com.facebook.stetho.Stetho
import ru.neoanon.openweather.utils.logging.ReleaseTree
import timber.log.Timber

/**
 *Created by eshtefan on  08.11.2018.
 */

class App : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build();
    }

    fun getAppComponent(): AppComponent = appComponent
}
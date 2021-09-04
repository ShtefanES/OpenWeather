package ru.neoanon.openweather.app

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import ru.neoanon.openweather.BuildConfig
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import ru.neoanon.openweather.utils.logging.ReleaseTree
import timber.log.Timber
import javax.inject.Inject

/**
 *Created by eshtefan on  08.11.2018.
 */

class App : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> =
        dispatchingAndroidInjector
}
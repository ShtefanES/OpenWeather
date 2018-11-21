package ru.neoanon.openweather.data.source.remote

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.neoanon.openweather.BuildConfig
import javax.inject.Singleton

/**
 *Created by eshtefan on  08.11.2018.
 */

@Module
class OwmApiModule {

    @Singleton
    @Provides
    fun providesOwmApi(): OpenWeatherMap {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addNetworkInterceptor(StethoInterceptor())
        }

        val retrofit = Retrofit.Builder()
            .client(okHttpBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.OWM_API_URL)
            .build()

        return retrofit.create(OpenWeatherMap::class.java)
    }
}
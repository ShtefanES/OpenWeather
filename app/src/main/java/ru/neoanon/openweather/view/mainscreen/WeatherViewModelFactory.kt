package ru.neoanon.openweather.view.mainscreen

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.neoanon.openweather.data.source.IForecastsRepo
import ru.neoanon.openweather.data.source.geodata.IRegionSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by eshtefan on  15.11.2018.
 */

@Singleton
class WeatherViewModelFactory @Inject constructor(
    private val repo: IForecastsRepo,
    private val regionSource: IRegionSource
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repo, regionSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
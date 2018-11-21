package ru.neoanon.openweather.view.detailedforecast

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.neoanon.openweather.data.source.IForecastsRepo
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by eshtefan on  14.11.2018.
 */

@Singleton
class DailyForecastViewModelFactory @Inject constructor(private val repo: IForecastsRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyForecastViewModel::class.java)) {
            return DailyForecastViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
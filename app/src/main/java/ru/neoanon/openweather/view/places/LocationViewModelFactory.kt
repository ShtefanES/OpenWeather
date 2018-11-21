package ru.neoanon.openweather.view.places

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.neoanon.openweather.data.source.geodata.IRegionSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by eshtefan on  16.11.2018.
 */

@Singleton
class LocationViewModelFactory @Inject constructor(private val iRegionSource: IRegionSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            return LocationViewModel(iRegionSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
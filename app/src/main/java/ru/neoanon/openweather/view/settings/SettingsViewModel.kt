package ru.neoanon.openweather.view.settings

import android.arch.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import io.reactivex.subjects.BehaviorSubject
import ru.neoanon.openweather.data.source.IForecastsRepo
import ru.neoanon.openweather.model.UnitsOfWeather
import timber.log.Timber

/**
 *Created by eshtefan on  19.11.2018.
 */

class SettingsViewModel(private val iForecastRepo: IForecastsRepo) : ViewModel() {
    private val originalSettingsSub = BehaviorSubject.create<UnitsOfWeather>()

    fun subscribeToUnits(): Observable<UnitsOfWeather> =
        iForecastRepo.getUnits()
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { unit -> originalSettingsSub.onNext(unit) }
            .doOnError { throwable ->
                Timber.e(throwable, "error in subscribeToUnits ")
            }

    fun saveSettings(unit: UnitsOfWeather): Completable =
        Observable.fromCallable {
            val originSettings = originalSettingsSub.value
            originSettings
        }.filter { originalUnits -> (originalUnits != unit) }
            .map {
                iForecastRepo.setUnits(unit)
                iForecastRepo.deleteAllForecasts()
                iForecastRepo.makeCacheDirty()
                1
            }.ignoreElements()
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable ->
                Timber.e(throwable, "error in saveSettings ")
            }
}
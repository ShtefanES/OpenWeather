package ru.neoanon.openweather.view.places

import android.arch.lifecycle.ViewModel
import android.support.v7.widget.SearchView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.neoanon.openweather.data.source.geodata.IRegionSource
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation
import ru.neoanon.openweather.utils.searchViewQueryTextListener
import timber.log.Timber

/**
 *Created by eshtefan on  16.11.2018.
 */

class LocationViewModel(private val iRegionSource: IRegionSource) : ViewModel() {
    val isSuggestionVisible = PublishSubject.create<Boolean>()
    val isPlacesVisible = PublishSubject.create<Boolean>()
    val isTitleVisible = PublishSubject.create<Boolean>()

    companion object {}

    fun setTitleVisible(b: Boolean) = isTitleVisible.onNext(b)

    fun addressSuggestions(searchView: SearchView): Observable<List<RegionLocation>> =
        LocationViewModel.searchViewQueryTextListener(searchView)
            .doOnNext { s ->
                if (s.isEmpty()) {
                    isSuggestionVisible.onNext(false)
                    isPlacesVisible.onNext(true)
                }
            }
            .filter { s -> (s.isNotEmpty() && s.length > 3 && s.length < 20) }
            .flatMap { s -> iRegionSource.getAddressSuggestions(s) }
            .doOnNext { regionLocations ->
                if (regionLocations.size != 0) {
                    isPlacesVisible.onNext(false)
                    isSuggestionVisible.onNext(true)
                } else {
                    isSuggestionVisible.onNext(false)
                    isPlacesVisible.onNext(true)
                }
            }
            .doOnError { throwable -> Timber.e(throwable, "error in addressSuggestions ") }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
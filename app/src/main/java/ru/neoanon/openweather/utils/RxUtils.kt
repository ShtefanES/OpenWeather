package ru.neoanon.openweather.utils

import android.support.v7.widget.SearchView
import android.view.View
import io.reactivex.Observable
import ru.neoanon.openweather.view.places.LocationViewModel

/**
 *Created by eshtefan on  16.11.2018.
 */

fun LocationViewModel.Companion.searchViewQueryTextListener(searchView: SearchView): Observable<String> =
    Observable.create { emitter ->
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) emitter.onNext(newText)
                return false
            }
        })

        emitter.setCancellable { searchView.setOnQueryTextListener(null) }
    }

fun LocationViewModel.Companion.searchViewCloseListener(searchView: SearchView): Observable<Any> =
    Observable.create { emitter ->
        searchView.setOnCloseListener {
            emitter.onNext(Any())
            false
        }
        emitter.setCancellable { searchView.setOnCloseListener(null) }
    }


fun LocationViewModel.Companion.searchViewFocusChangeListener(searchView: SearchView): Observable<Boolean> =
    Observable.create { emitter ->
        searchView.setOnQueryTextFocusChangeListener { _, b -> emitter.onNext(b) }

        emitter.setCancellable { searchView.setOnQueryTextFocusChangeListener(null) }
    }


fun LocationViewModel.Companion.clickView(view: View): Observable<Any> =
    Observable.create { emitter ->
        view.setOnClickListener { emitter.onNext(Any()) }

        emitter.setCancellable({ view.setOnClickListener(null) })
    }
package ru.neoanon.openweather.view.detailedforecast

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.neoanon.openweather.R
import ru.neoanon.openweather.data.source.IForecastsRepo
import ru.neoanon.openweather.model.DailyForecastItem
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 *Created by eshtefan on  14.11.2018.
 */

class DailyForecastViewModel @Inject constructor(private val repo: IForecastsRepo) : ViewModel() {

	val progress: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
	private val errorSub = PublishSubject.create<Throwable>()

	fun subscribeToDailyForecasts(regionId: Long): Observable<List<DailyForecastItem>> =
		repo.getAllDailyForecasts(regionId)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.doOnSubscribe { progress.onNext(true) }
			.doOnError { throwable ->
				progress.onNext(false)
				Timber.e(throwable, "error in subscribeToDailyForecasts ")
				errorSub.onNext(throwable)
			}
			.doOnNext { progress.onNext(false) }

	fun errorObservable(): Observable<Int> =
		errorSub.map { throwable -> if (throwable is IOException) R.string.network_error else R.string.general_error }
}
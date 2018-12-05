package ru.neoanon.openweather.view.mainscreen

import android.arch.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.neoanon.openweather.R
import ru.neoanon.openweather.data.source.IForecastsRepo
import ru.neoanon.openweather.data.source.geodata.IRegionSource
import ru.neoanon.openweather.data.source.local.db.location.RegionLocation
import ru.neoanon.openweather.model.CommonHourlyItem
import ru.neoanon.openweather.model.CurrentWeatherItem
import ru.neoanon.openweather.model.DailyForecastShortItem
import ru.neoanon.openweather.utils.CURRENT_LOCATION_ID
import timber.log.Timber
import java.io.IOException

/**
 *Created by eshtefan on  15.11.2018.
 */

class WeatherViewModel(private val forecastsRepo: IForecastsRepo, private val iRegionSource: IRegionSource) :
    ViewModel() {
    val closeDrawerSub = PublishSubject.create<Any>()
    val regionWasChangedSub = PublishSubject.create<Long>()
    val progress = BehaviorSubject.createDefault<Boolean>(false)
    private val errorSub = PublishSubject.create<Throwable>()
    private val isAllowedToGetCurrentLocation = BehaviorSubject.createDefault<Boolean>(false)
    private val selectedRegionNameSub = BehaviorSubject.createDefault<String>("Unknown")
    val selectedRegionIdSub = BehaviorSubject.createDefault<Long>(CURRENT_LOCATION_ID)

    fun isAllowedToGetCurrentLocation(isAllowed: Boolean) {
        isAllowedToGetCurrentLocation.onNext(isAllowed)
    }

    fun subscribeToData(regionId: Long): Observable<RegionLocation> =
        Observable.fromCallable { regionId }
            .flatMap { id ->
                if (id == CURRENT_LOCATION_ID) {
                    iRegionSource.getCurrentRegionLocation()
                        .flatMap { regionLocation ->
                            regionLocation.id = CURRENT_LOCATION_ID
                            forecastsRepo.addRegionLocation(regionLocation)
                            Observable.just(regionLocation)
                        }
                } else {
                    forecastsRepo.getRegionLocation(id)
                }
            }.flatMap { regionLocation ->
                selectedRegionNameSub.onNext(regionLocation.name)
                forecastsRepo.updateData(regionLocation)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progress.onNext(true) }
            .doOnComplete { progress.onNext(false) }
            .doOnError { throwable ->
                Timber.e(throwable, "error in subscribeToData ")
                errorSub.onNext(throwable)
                progress.onNext(false)
            }
            .doOnNext { regionLocation ->
                selectedRegionIdSub.onNext(regionLocation.id)
                progress.onNext(false);
            }

    fun subscribeSelectedRegionName(): Observable<String> =
        selectedRegionNameSub
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { throwable -> Timber.e(throwable, "error in subscribeSelectedRegionName ") }


    fun selectRegionLocation(regionLocation: RegionLocation): Observable<RegionLocation> =
        Observable.fromCallable {
            val regionLocation1 = forecastsRepo.getRegionLocation(regionLocation.name)
            val resultLocation = if (regionLocation1 == null) {
                val regionId = forecastsRepo.addRegionLocation(regionLocation)
                regionLocation.id = regionId
                regionLocation
            } else {
                regionLocation1
            }
            forecastsRepo.makeCacheDirty()
            selectedRegionNameSub.onNext(resultLocation.name)
            resultLocation
        }
            .flatMap { resultLocation -> forecastsRepo.updateData(resultLocation) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progress.onNext(true) }
            .doOnError { throwable ->
                Timber.e(throwable, "error in selectRegionLocation ")
                errorSub.onNext(throwable)
                progress.onNext(false)
            }
            .doOnNext { fullRegionLocation ->
                if (selectedRegionIdSub.value != fullRegionLocation.id) {
                    selectedRegionIdSub.onNext(fullRegionLocation.id)
                    regionWasChangedSub.onNext(fullRegionLocation.id)
                }
                progress.onNext(false)
            }

    fun deleteRegionLocation(regionId: Long): Observable<RegionLocation> =
        forecastsRepo.deleteAllInfoForThisRegion(regionId)
            .andThen(Observable.just(regionId))
            .filter { regionId1 ->
                regionId1 == selectedRegionIdSub.value
            }
            .filter {
                val isAvailable = isAllowedToGetCurrentLocation.value
                if (!isAvailable) {
                    forecastsRepo.makeCacheDirty()
                    selectedRegionNameSub.onNext("Unknown")
                    selectedRegionIdSub.onNext(CURRENT_LOCATION_ID)
                }
                isAvailable
            }
            .flatMap { forecastsRepo.getRegionLocation(CURRENT_LOCATION_ID) }
            .flatMap { regionLocation ->
                forecastsRepo.makeCacheDirty()
                selectedRegionNameSub.onNext(regionLocation.name)
                forecastsRepo.updateData(regionLocation)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progress.onNext(true) }
            .doOnError { throwable ->
                progress.onNext(false)
                Timber.e(throwable, "error in deleteRegionLocation ")
                errorSub.onNext(throwable)
            }
            .doOnComplete { progress.onNext(false) }
            .doOnNext { regionLocation ->
                selectedRegionIdSub.onNext(regionLocation.id)
                regionWasChangedSub.onNext(regionLocation.id)
                progress.onNext(false)
            }

    fun subscribeRegionLocations(): Flowable<List<RegionLocation>> =
        forecastsRepo.getAllRegionLocations()
            .doOnError { throwable ->
                Timber.e(throwable, "error in subscribeRegionLocations ")
                errorSub.onNext(throwable)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    fun subscribeDailyForecasts(regionId: Long): Flowable<List<DailyForecastShortItem>> =
        forecastsRepo.getAllShortDailyForecasts(regionId)
            .doOnError { throwable ->
                Timber.e(throwable, "error in subscribeDailyForecasts ")
                errorSub.onNext(throwable)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun subscribeHourlyForecasts(regionId: Long): Flowable<List<CommonHourlyItem>> =
        forecastsRepo.getAllCommonHourlyForecasts(regionId)
            .doOnError { throwable ->
                Timber.e(throwable, "error in subscribeHourlyForecasts ")
                errorSub.onNext(throwable)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun subscribeCurrentWeather(regionId: Long): Flowable<CurrentWeatherItem> =
        forecastsRepo.getCurrentWeather(regionId)
            .doOnError { throwable ->
                Timber.e(throwable, "error in subscribeCurrentWeather ")
                errorSub.onNext(throwable)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun errorObservable(): Observable<Int> =
        errorSub.map { throwable ->
            if (throwable is IOException) R.string.network_error
            else R.string.general_error
        }

    fun closeDrawer() =
        closeDrawerSub.onNext(Any())
}
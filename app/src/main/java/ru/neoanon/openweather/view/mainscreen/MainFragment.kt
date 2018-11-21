package ru.neoanon.openweather.view.mainscreen

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable
import ru.neoanon.openweather.R
import ru.neoanon.openweather.app.App
import ru.neoanon.openweather.databinding.FragmentMainBinding
import ru.neoanon.openweather.utils.CURRENT_LOCATION_ID
import ru.neoanon.openweather.view.detailedforecast.ForecastActivity
import ru.neoanon.openweather.view.detailedforecast.ForecastPagerFragment
import timber.log.Timber
import javax.inject.Inject

/**
 *Created by eshtefan on  13.11.2018.
 */

class MainFragment : Fragment() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 101
    private val disposable = CompositeDisposable()
    private val weatherDisposable = CompositeDisposable()

    private var selectedRegionId = 0L
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var tvTemp: TextView
    private lateinit var iconWeather: ImageView
    private lateinit var tvWeatherDescription: TextView
    private lateinit var ivWeatherBackground: ImageView
    private lateinit var currentWeatherBlock: View

    @Inject
    lateinit var dailyForecastAdapter: DailyForecastAdapter
    @Inject
    lateinit var hourlyForecastAdapter: HourlyForecastAdapter
    @Inject
    lateinit var weatherViewModelFactory: WeatherViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        (activity?.application as App).getAppComponent().inject(this)

        val dailyForecastRecyclerView = binding.shortDailyForecastRecyclerView
        dailyForecastRecyclerView.adapter = dailyForecastAdapter
        dailyForecastRecyclerView.isNestedScrollingEnabled = false

        val hourlyForecastRecyclerView = binding.hourlyForecastRecyclerView
        hourlyForecastRecyclerView.adapter = hourlyForecastAdapter
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        hourlyForecastRecyclerView.layoutManager = horizontalLayoutManager

        swipeRefresh = binding.swipeContainer
        tvTemp = binding.tvTemp
        iconWeather = binding.iconWeather
        tvWeatherDescription = binding.tvWeatherDescription
        ivWeatherBackground = binding.ivWeatherBackground
        currentWeatherBlock = binding.currentWeatherBlock

        ivWeatherBackground.setImageResource(R.drawable.ic_background_clear)
        changeWeatherBackgroundScale(ivWeatherBackground)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        weatherViewModel = ViewModelProviders.of(activity!!, weatherViewModelFactory).get(WeatherViewModel::class.java)

        dailyForecastAdapter.clickListener = { position ->
            if (swipeRefresh.isRefreshing) {
                showMessage(R.string.wait_text)
            } else {
                val intent = Intent(activity, ForecastActivity::class.java)
                intent.putExtra(ForecastPagerFragment.FORECAST_POSITION_KEY, position)
                intent.putExtra(ForecastPagerFragment.REGION_ID_KEY, selectedRegionId)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        disposable.add(weatherViewModel.selectedRegionIdSub
            .subscribe { regionId ->
                selectedRegionId = regionId
                Timber.w("selected region id: %s", selectedRegionId)
            })

        disposable.add(weatherViewModel.progress
            .subscribe { aBoolean ->
                swipeRefresh.isRefreshing = aBoolean
            })

        disposable.add(weatherViewModel.regionWasChangedSub
            .subscribe { regionId ->
                unsubscribeToChangeUIWeatherLists()
                subscribeToChangeUIWeatherLists(regionId)
            })

        disposable.add(weatherViewModel.errorObservable()
            .subscribe { integer -> showMessage(integer) })

        if (selectedRegionId == CURRENT_LOCATION_ID) {
            if (!isPermissionGranted(activity!!)) {
                requestPermission(activity!!)
            } else {
                subscribeToData(selectedRegionId)
            }
        } else {
            subscribeToData(selectedRegionId)
        }

        subscribeToChangeUIWeatherLists(selectedRegionId)

        swipeRefresh.setOnRefreshListener {
            if (selectedRegionId == CURRENT_LOCATION_ID) {
                if (!isPermissionGranted(activity!!)) {
                    requestPermission(activity!!)
                } else {
                    subscribeToData(selectedRegionId)
                }
            } else {
                subscribeToData(selectedRegionId)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    subscribeToData(CURRENT_LOCATION_ID)
                } else {
                    weatherViewModel.progress.onNext(false)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
        unsubscribeToChangeUIWeatherLists()
        swipeRefresh.setOnRefreshListener(null)
    }

    private fun isPermissionGranted(context: Context): Boolean =
        (checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

    private fun requestPermission(context: Context) {
        AlertDialog.Builder(context)
            .setMessage(R.string.pre_request_for_permission_request)
            .setPositiveButton(R.string.btn_name_continue)
            { _, _ ->
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE
                )
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun subscribeToData(regionId: Long) =
        disposable.add(
            weatherViewModel.subscribeToData(regionId)
                .subscribe({}, {})
        )


    private fun subscribeToChangeUIWeatherLists(regionId: Long) {
        weatherDisposable.add(
            weatherViewModel.subscribeDailyForecasts(regionId)
                .subscribe({ forecastShortItems ->
                    dailyForecastAdapter.clear()
                    dailyForecastAdapter.add(forecastShortItems)
                    dailyForecastAdapter.notifyDataSetChanged()
                },
                    {})
        )

        weatherDisposable.add(
            weatherViewModel.subscribeHourlyForecasts(regionId)
                .subscribe({ commonHourlyItems ->
                    hourlyForecastAdapter.clear()
                    hourlyForecastAdapter.add(commonHourlyItems)
                    hourlyForecastAdapter.notifyDataSetChanged()
                }, {
                })
        )

        weatherDisposable.add(
            weatherViewModel.subscribeCurrentWeather(regionId)
                .subscribe({ currentWeatherItem ->
                    currentWeatherBlock.visibility = if (currentWeatherItem.iconId == 0) View.GONE else View.VISIBLE

                    if (currentWeatherItem.iconId != 0) {
                        currentWeatherBlock.setVisibility(View.VISIBLE)
                        tvTemp.text = currentWeatherItem.temp
                        iconWeather.setImageResource(currentWeatherItem.iconId)
                        tvWeatherDescription.text = currentWeatherItem.weatherDescription

                        ivWeatherBackground.setImageResource(currentWeatherItem.backgroundIconId)
                    }
                }, {
                })
        )
    }

    private fun unsubscribeToChangeUIWeatherLists() = weatherDisposable.clear()


    private fun changeWeatherBackgroundScale(ivBackground: ImageView) {
        val matrix = ivBackground.imageMatrix
        val imageWidth: Float = ivBackground.drawable.intrinsicWidth.toFloat()
        val screenWidth: Float = resources.displayMetrics.widthPixels.toFloat()
        val scaleRatio = if (screenWidth < 500f) screenWidth / imageWidth + 1.0f else screenWidth / imageWidth + 0.2f
        matrix.postScale(scaleRatio, scaleRatio)
        ivBackground.imageMatrix = matrix
    }

    private fun showMessage(resIdString: Int) =
        Toast.makeText(activity, getString(resIdString), Toast.LENGTH_SHORT).show()
}

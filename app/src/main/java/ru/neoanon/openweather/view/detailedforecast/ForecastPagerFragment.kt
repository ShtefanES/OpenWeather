package ru.neoanon.openweather.view.detailedforecast

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import io.reactivex.disposables.CompositeDisposable
import ru.neoanon.openweather.R
import ru.neoanon.openweather.app.App
import kotlinx.android.synthetic.main.fragment_forecast_pager.view.*
import kotlinx.android.synthetic.main.toolbar_with_tabs_layout.*
import ru.neoanon.openweather.di.daggerInject
import javax.inject.Inject

/**
 *Created by eshtefan on  13.11.2018.
 */

class ForecastPagerFragment : Fragment() {
    companion object {
        const val FORECAST_POSITION_KEY = "forecastPositionKey"
        const val REGION_ID_KEY = "regionIdKey"

        fun newInstance(selectedForecastPosition: Int, regionId: Long): ForecastPagerFragment {
            val args = Bundle()
            val f = ForecastPagerFragment()
            args.putInt(FORECAST_POSITION_KEY, selectedForecastPosition)
            args.putLong(REGION_ID_KEY, regionId)
            f.arguments = args
            return f
        }
    }

    private val disposable = CompositeDisposable()
    private var selectedForecastPosition = 0
    private var regionId = 0L
    private lateinit var viewPager: ViewPager
    private lateinit var progressBar: ProgressBar
    private lateinit var pagerAdapter: PagerForecastAdapter

    @Inject
    lateinit var forecastViewModelFactory: DailyForecastViewModelFactory

    override fun onAttach(context: Context?) {
        daggerInject()
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle: Bundle? = arguments
        bundle?.getInt(FORECAST_POSITION_KEY)?.let { selectedForecastPosition = it }
        bundle?.getLong(REGION_ID_KEY)?.let { regionId = it }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_forecast_pager, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.viewpager
        progressBar = view.progress_bar
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.tab_layout?.setupWithViewPager(viewPager)

        val forecastViewModel =
            ViewModelProviders.of(activity!!, forecastViewModelFactory).get(DailyForecastViewModel::class.java)

        disposable.add(forecastViewModel.progress
            .subscribe { aBoolean -> progressBar.visibility = if (aBoolean) View.VISIBLE else View.GONE })

        disposable.add(
            forecastViewModel.subscribeToDailyForecasts(regionId)
                .subscribe({ dailyForecastItems ->
                    pagerAdapter = PagerForecastAdapter(childFragmentManager, dailyForecastItems)
                    viewPager.adapter = pagerAdapter
                    viewPager.currentItem = selectedForecastPosition
                }, { })
        )

        disposable.add(
            forecastViewModel.errorObservable()
                .subscribe { integer -> showMessage(integer) })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun showMessage(resIdString: Int) {
        Toast.makeText(getActivity(), getString(resIdString), Toast.LENGTH_SHORT).show()
    }
}
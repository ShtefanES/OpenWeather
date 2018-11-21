package ru.neoanon.openweather.view.detailedforecast

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ru.neoanon.openweather.model.DailyForecastItem

/**
 *Created by eshtefan on  14.11.2018.
 */

class PagerForecastAdapter(fm: FragmentManager, private val forecasts: List<DailyForecastItem>) :
    FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment =
        ForecastItemFragment.newInstance(forecasts[p0])

    override fun getCount(): Int = forecasts.size

    override fun getPageTitle(position: Int): CharSequence? = forecasts[position].title
}
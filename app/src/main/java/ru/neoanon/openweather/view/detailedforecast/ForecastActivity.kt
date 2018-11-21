package ru.neoanon.openweather.view.detailedforecast

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import ru.neoanon.openweather.R
import ru.neoanon.openweather.databinding.ActivityForecastBinding

/**
 *Created by eshtefan on  13.11.2018.
 */

class ForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityForecastBinding = DataBindingUtil.setContentView(this, R.layout.activity_forecast)
        val toolbar = binding.toolbarContainer.toolbar
        setupToolbar(toolbar)

        val selectedForecastPosition = intent.getIntExtra(ForecastPagerFragment.FORECAST_POSITION_KEY, -1)
        val regionId = intent.getLongExtra(ForecastPagerFragment.REGION_ID_KEY, -1)
        if (selectedForecastPosition != -1 && regionId != -1L) {
            val pagerFragment = ForecastPagerFragment.newInstance(selectedForecastPosition, regionId)
            commitFragment(pagerFragment)
        }
    }

    private fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.ten_day_forecast_title)

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun commitFragment(fragment: Fragment) {
        val fTrans = supportFragmentManager.beginTransaction()
        fTrans.replace(R.id.daily_forecast_content, fragment)
        fTrans.commit()
    }
}
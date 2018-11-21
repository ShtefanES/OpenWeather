package ru.neoanon.openweather.view.detailedforecast

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.neoanon.openweather.R
import ru.neoanon.openweather.databinding.FragmentDailyForecastItemBinding
import ru.neoanon.openweather.model.DailyForecastItem

/**
 *Created by eshtefan on  13.11.2018.
 */

class ForecastItemFragment : Fragment() {
    companion object {
        private const val FORECAST_ITEM_KEY = "forecastItemKey"

        fun newInstance(item: DailyForecastItem): ForecastItemFragment {
            val args = Bundle()
            val f = ForecastItemFragment()
            args.putParcelable(FORECAST_ITEM_KEY, item)
            f.arguments = args
            return f
        }
    }

    private lateinit var dailyForecastItem: DailyForecastItem
    private lateinit var rainBlock: View
    private lateinit var snowBlock: View
    private lateinit var tvWithoutPrecipitation: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dailyForecastItem = arguments?.getParcelable(FORECAST_ITEM_KEY) as DailyForecastItem
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentDailyForecastItemBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_daily_forecast_item, container, false
        )

        binding.iconWeather.setImageResource(dailyForecastItem.weatherIconId)
        binding.tvWeatherDescription.text = dailyForecastItem.weatherDescription
        binding.tvMorningTemp.text = dailyForecastItem.morningTemp
        binding.tvDayTemp.text = dailyForecastItem.dayTemp
        binding.tvEveningTemp.text = dailyForecastItem.eveningTemp
        binding.tvNightTemp.text = dailyForecastItem.nightTemp
        binding.tvWind.text = dailyForecastItem.windDescription
        binding.iconWindDirect.setImageResource(dailyForecastItem.windDirectIconId)

        rainBlock = binding.rainBlock
        snowBlock = binding.snowBlock
        tvWithoutPrecipitation = binding.tvWithoutPrecipitation

        if (dailyForecastItem.isWithoutPrecipitation) {
            rainBlock.visibility = View.GONE
            snowBlock.visibility = View.GONE
            tvWithoutPrecipitation.visibility = View.VISIBLE
        } else {
            tvWithoutPrecipitation.visibility = View.GONE
            if (dailyForecastItem.rain != null) {
                rainBlock.visibility = View.VISIBLE
                binding.tvRain.text = dailyForecastItem.rain
            }
            if (dailyForecastItem.snow != null) {
                snowBlock.visibility = View.VISIBLE
                binding.tvSnow.text = dailyForecastItem.snow
            }
        }

        binding.tvPressure.text = dailyForecastItem.pressure
        binding.tvHumidity.text = dailyForecastItem.humidity

        return binding.root
    }
}
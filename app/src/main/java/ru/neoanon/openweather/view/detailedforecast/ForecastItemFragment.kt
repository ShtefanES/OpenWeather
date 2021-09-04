package ru.neoanon.openweather.view.detailedforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.neoanon.openweather.R
import ru.neoanon.openweather.model.DailyForecastItem
import kotlinx.android.synthetic.main.fragment_daily_forecast_item.view.*
import ru.neoanon.openweather.utils.args

/**
 *Created by eshtefan on  13.11.2018.
 */

class ForecastItemFragment : Fragment() {
    companion object {

        fun newInstance(item: DailyForecastItem): ForecastItemFragment =
            ForecastItemFragment().apply {
                Bundle().also { bundle ->
                    bundle.dailyForecastItem = item
                    arguments = bundle
                }
            }
    }

    private lateinit var dailyForecastItem: DailyForecastItem
    private lateinit var rainBlock: View
    private lateinit var snowBlock: View
    private lateinit var tvWithoutPrecipitation: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dailyForecastItem = args.dailyForecastItem
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_daily_forecast_item, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.icon_weather.setImageResource(dailyForecastItem.weatherIconId)
        view.tv_weather_description.text = dailyForecastItem.weatherDescription
        view.tv_morning_temp.text = dailyForecastItem.morningTemp
        view.tv_day_temp.text = dailyForecastItem.dayTemp
        view.tv_evening_temp.text = dailyForecastItem.eveningTemp
        view.tv_night_temp.text = dailyForecastItem.nightTemp
        view.tv_wind.text = dailyForecastItem.windDescription
        view.icon_wind_direct.setImageResource(dailyForecastItem.windDirectIconId)

        rainBlock = view.rain_block
        snowBlock = view.snow_block
        tvWithoutPrecipitation = view.tv_without_precipitation

        if (dailyForecastItem.isWithoutPrecipitation) {
            rainBlock.visibility = View.GONE
            snowBlock.visibility = View.GONE
            tvWithoutPrecipitation.visibility = View.VISIBLE
        } else {
            tvWithoutPrecipitation.visibility = View.GONE
            if (dailyForecastItem.rain != null) {
                rainBlock.visibility = View.VISIBLE
                view.tv_rain.text = dailyForecastItem.rain
            }
            if (dailyForecastItem.snow != null) {
                snowBlock.visibility = View.VISIBLE
                view.tv_snow.text = dailyForecastItem.snow
            }
        }

        view.tv_pressure.text = dailyForecastItem.pressure
        view.tv_humidity.text = dailyForecastItem.humidity
    }
}
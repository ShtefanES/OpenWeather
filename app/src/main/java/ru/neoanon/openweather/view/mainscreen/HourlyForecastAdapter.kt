package ru.neoanon.openweather.view.mainscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_header_of_hourly_forecast.view.*
import kotlinx.android.synthetic.main.item_hourly_forecast.view.*
import ru.neoanon.openweather.R
import ru.neoanon.openweather.model.CommonHourlyItem
import ru.neoanon.openweather.model.HeaderHourlyForecastItem
import ru.neoanon.openweather.model.HourlyForecastItem
import javax.inject.Inject

/**
 *Created by eshtefan on  15.11.2018.
 */

class HourlyForecastAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val WEATHER_ITEM = 0
    private val FORECAST_ITEM = 1
    private val forecasts = arrayListOf<CommonHourlyItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == WEATHER_ITEM) {
            WeatherHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_header_of_hourly_forecast,
                    parent,
                    false
                )
            )
        } else {
            ForecastHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_hourly_forecast,
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeatherHolder -> {
                val header = forecasts[position] as HeaderHourlyForecastItem
                holder.root.icon_wind_direct.setImageResource(header.windDirectIconId)
                holder.root.tv_wind.text = header.windDescription
                holder.root.tv_pressure.text = header.pressure
                holder.root.tv_humidity.text = header.humidity
            }
            is ForecastHolder -> {
                val hourlyForecast = forecasts[position] as HourlyForecastItem
                holder.root.tv_temp.text = hourlyForecast.temp
                holder.root.tv_time.text = if (position == 1) context.getString(R.string.now) else hourlyForecast.time
                holder.root.icon_weather.setImageResource(hourlyForecast.iconId)
            }
        }
    }

    override fun getItemCount(): Int = forecasts.size

    override fun getItemViewType(position: Int): Int {
        val commonHourlyItem = forecasts[position]
        return when (commonHourlyItem) {
            is HeaderHourlyForecastItem -> WEATHER_ITEM
            else -> FORECAST_ITEM
        }
    }

    fun clear() = forecasts.clear()

    fun add(forecasts: List<CommonHourlyItem>) = this.forecasts.addAll(forecasts)

    class WeatherHolder(val root: View) : RecyclerView.ViewHolder(root)

    class ForecastHolder(val root: View) : RecyclerView.ViewHolder(root)
}
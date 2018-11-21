package ru.neoanon.openweather.view.mainscreen

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.neoanon.openweather.R
import ru.neoanon.openweather.databinding.ItemHeaderOfHourlyForecastBinding
import ru.neoanon.openweather.databinding.ItemHourlyForecastBinding
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        if (viewType == WEATHER_ITEM) {
            val binding: ItemHeaderOfHourlyForecastBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_header_of_hourly_forecast, parent, false)
            return WeatherHolder(binding)
        } else {
            val binding: ItemHourlyForecastBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_hourly_forecast, parent, false)
            return ForecastHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeatherHolder -> {
                val header = forecasts[position] as HeaderHourlyForecastItem
                holder.binding.iconWindDirect.setImageResource(header.windDirectIconId)
                holder.binding.tvWind.text = header.windDescription
                holder.binding.tvPressure.text = header.pressure
                holder.binding.tvHumidity.text = header.humidity
            }
            is ForecastHolder -> {
                val hourlyForecast = forecasts[position] as HourlyForecastItem
                holder.binding.tvTemp.text = hourlyForecast.temp
                holder.binding.tvTime.text = if (position == 1) context.getString(R.string.now) else hourlyForecast.time
                holder.binding.iconWeather.setImageResource(hourlyForecast.iconId)
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

    class WeatherHolder(val binding: ItemHeaderOfHourlyForecastBinding) : RecyclerView.ViewHolder(binding.root)

    class ForecastHolder(val binding: ItemHourlyForecastBinding) : RecyclerView.ViewHolder(binding.root)
}
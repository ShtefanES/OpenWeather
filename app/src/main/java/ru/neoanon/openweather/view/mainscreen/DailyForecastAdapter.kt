package ru.neoanon.openweather.view.mainscreen

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.neoanon.openweather.R
import ru.neoanon.openweather.databinding.ItemShortDailyForecastBinding
import ru.neoanon.openweather.model.DailyForecastShortItem
import javax.inject.Inject

/**
 *Created by eshtefan on  15.11.2018.
 */
typealias DailyForecastItemClicked = (position: Int) -> Unit

class DailyForecastAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<DailyForecastAdapter.MyViewHolder>() {

    private val forecastsList = arrayListOf<DailyForecastShortItem>()
    var clickListener: DailyForecastItemClicked? = null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val binding: ItemShortDailyForecastBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_short_daily_forecast, p0, false)
        return DailyForecastAdapter.MyViewHolder(binding, this)
    }

    override fun getItemCount(): Int = forecastsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val forecast = forecastsList[position]

        holder.binding.tvDayOfWeek.text = forecast.dayOfWeek
        holder.binding.tvTemp.text = forecast.temp
        holder.binding.tvDayOfWeek.setTextColor(ContextCompat.getColor(context, forecast.dayOfWeekColorId))

        holder.binding.tvDayOfMonth.text = when (position) {
            0 -> context.getString(R.string.today)
            1 -> context.getString(R.string.tomorrow)
            else -> forecast.dayOfMonth
        }
        holder.binding.iconWeather.setImageResource(forecast.iconId)
    }

    fun clear() = this.forecastsList.clear()

    fun add(forecasts: List<DailyForecastShortItem>) = this.forecastsList.addAll(forecasts)

    class MyViewHolder(val binding: ItemShortDailyForecastBinding, adapter: DailyForecastAdapter) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { adapter.clickListener?.invoke(adapterPosition) }
        }
    }
}
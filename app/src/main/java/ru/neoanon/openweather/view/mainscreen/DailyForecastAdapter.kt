package ru.neoanon.openweather.view.mainscreen

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_short_daily_forecast.view.*
import ru.neoanon.openweather.R
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


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder =
        DailyForecastAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_short_daily_forecast,
                parent,
                false
            ), this
        )


    override fun getItemCount(): Int = forecastsList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val forecast = forecastsList[position]

        holder.root.tv_day_of_week.text = forecast.dayOfWeek
        holder.root.tv_temp.text = forecast.temp
        holder.root.tv_day_of_week.setTextColor(ContextCompat.getColor(context, forecast.dayOfWeekColorId))

        holder.root.tv_day_of_month.text = when (position) {
            0 -> context.getString(R.string.today)
            1 -> context.getString(R.string.tomorrow)
            else -> forecast.dayOfMonth
        }
        holder.root.icon_weather.setImageResource(forecast.iconId)
    }

    fun clear() = this.forecastsList.clear()

    fun add(forecasts: List<DailyForecastShortItem>) = this.forecastsList.addAll(forecasts)

    class MyViewHolder(val root: View, adapter: DailyForecastAdapter) :
        RecyclerView.ViewHolder(root) {
        init {
            root.setOnClickListener { adapter.clickListener?.invoke(adapterPosition) }
        }
    }
}
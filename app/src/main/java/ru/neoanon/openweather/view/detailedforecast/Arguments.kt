package ru.neoanon.openweather.view.detailedforecast

import android.os.Bundle
import ru.neoanon.openweather.model.DailyForecastItem

private const val FORECAST_ITEM_KEY = "forecastItemKey"
var Bundle.dailyForecastItem: DailyForecastItem
	get() = getSerializable(FORECAST_ITEM_KEY) as DailyForecastItem
	set(value) = putSerializable(FORECAST_ITEM_KEY, value)
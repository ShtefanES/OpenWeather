package ru.neoanon.openweather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *Created by eshtefan on  12.11.2018.
 */


@Parcelize
data class DailyForecastItem(
    val title: String,
    val morningTemp: String,
    val dayTemp: String,
    val eveningTemp: String,
    val nightTemp: String,
    val weatherIconId: Int,
    val weatherDescription: String,
    val windDirectIconId: Int,
    val windDescription: String,
    val snow: String?,
    val rain: String?,
    val isWithoutPrecipitation: Boolean,
    val pressure: String,
    val humidity: String
) : Parcelable
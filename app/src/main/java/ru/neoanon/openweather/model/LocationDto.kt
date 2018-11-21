package ru.neoanon.openweather.model

import com.google.gson.annotations.SerializedName

/**
 *Created by eshtefan on  11.11.2018.
 */

data class LocationDto(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double
)
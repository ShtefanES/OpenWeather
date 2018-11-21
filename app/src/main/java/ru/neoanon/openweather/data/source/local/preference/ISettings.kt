package ru.neoanon.openweather.data.source.local.preference

import ru.neoanon.openweather.model.UnitsOfWeather

/**
 *Created by eshtefan on  11.11.2018.
 */

interface ISettings {
    fun saveTempType(type: String)

    fun getTempType(): String

    fun savePressureType(pressure: String)

    fun getPressureType(): String

    fun saveDateOfDirtyCache(timestamp: Long)

    fun getDateOfDirtyCache(): Long

    fun getUnit(): UnitsOfWeather
}
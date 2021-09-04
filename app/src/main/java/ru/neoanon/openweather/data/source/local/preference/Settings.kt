package ru.neoanon.openweather.data.source.local.preference

import android.content.SharedPreferences
import ru.neoanon.openweather.model.UnitsOfWeather
import ru.neoanon.openweather.model.enumerations.PressureType
import ru.neoanon.openweather.model.enumerations.TempType
import javax.inject.Inject

/**
 *Created by eshtefan on  11.11.2018.
 */

class Settings @Inject constructor(private val sharedPreferences: SharedPreferences) : ISettings {
    private val KEY_TEMP: String = "keyTemp"
    private val KEY_PRESSURE: String = "keyPressure"
    private val KEY_DATE_OF_DIRTY_CACHE: String = "keyDateOfDirtyCache"

    override fun saveTempType(type: String) {
        val ed = this.sharedPreferences.edit()
        ed.putString(KEY_TEMP, type)
        ed.apply()
    }

    override fun getTempType(): String = sharedPreferences.getString(KEY_TEMP, TempType.CELSIUS.id) ?: TempType.CELSIUS.id

    override fun savePressureType(pressure: String) {
        val ed = sharedPreferences.edit()
        ed.putString(KEY_PRESSURE, pressure)
        ed.apply()
    }

    override fun getPressureType(): String = sharedPreferences.getString(KEY_PRESSURE, PressureType.TORR.id) ?: PressureType.TORR.id

    override fun saveDateOfDirtyCache(timestamp: Long) {
        val ed = sharedPreferences.edit()
        ed.putLong(KEY_DATE_OF_DIRTY_CACHE, timestamp)
        ed.apply()
    }

    override fun getDateOfDirtyCache() = sharedPreferences.getLong(KEY_DATE_OF_DIRTY_CACHE, -1L)

    override fun getUnit() = UnitsOfWeather(getTempType(), getPressureType())
}
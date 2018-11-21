package ru.neoanon.openweather.utils

import ru.neoanon.openweather.model.enumerations.CardinalPoints
import ru.neoanon.openweather.model.enumerations.DegreeType
import ru.neoanon.openweather.model.enumerations.TempType
import java.lang.IllegalArgumentException
import kotlin.math.round

/**
 *Created by eshtefan on  13.11.2018.
 */

fun getDirection(deg: Double): String {
    return if (deg == 0.0 || deg == 360.0) {
        CardinalPoints.N.name
    } else if (deg > 0.0 && deg < 90.0) {
        CardinalPoints.NE.name
    } else if (deg == 90.0) {
        CardinalPoints.E.name
    } else if (deg > 90.0 && deg < 180.0) {
        CardinalPoints.SE.name
    } else if (deg == 180.0) {
        CardinalPoints.S.name
    } else if (deg > 180.0 && deg < 270.0) {
        CardinalPoints.SW.name
    } else if (deg == 270.0) {
        CardinalPoints.W.name
    } else if (deg > 270 && deg < 360) {
        CardinalPoints.NW.name
    } else {
        throw IllegalArgumentException()
    }
}

fun hectoPascalToTorr(hectoPascal: Double): Int {
    val hectoPascalFactor = 0.7500616827// 1 hPa = 0.7500616827 mmHg
    return round(hectoPascal * hectoPascalFactor).toInt()
}

fun buildTemperatureStr(t: Int, type: String?): String {
    val tempType =
        if (type == null) DegreeType.DEGREE.id else if (type == TempType.CELSIUS.id) DegreeType.DEGREE_CELSIUS.id else DegreeType.DEGREE_KELVIN.id
    return if (t > 0) "+$t $tempType" else "$t $tempType"
}
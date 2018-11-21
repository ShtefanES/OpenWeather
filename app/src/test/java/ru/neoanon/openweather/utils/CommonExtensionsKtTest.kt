package ru.neoanon.openweather.utils

import org.junit.Test

import org.junit.Assert.*
import ru.neoanon.openweather.model.enumerations.CardinalPoints
import ru.neoanon.openweather.model.enumerations.TempType
import java.lang.IllegalArgumentException

/**
 * Created by eshtefan on  20.11.2018.
 */

class CommonExtensionsKtTest {

    @Test
    fun getDirectionWhenDeg0() {
        val actualDirect = ru.neoanon.openweather.utils.getDirection(0.0)
        val expectedDirect = CardinalPoints.N.name
        assertEquals(expectedDirect, actualDirect)
    }

    @Test
    fun getDirectionWhenDeg360() {
        val actualDirect = ru.neoanon.openweather.utils.getDirection(360.0)
        val expectedDirect = CardinalPoints.N.name
        assertEquals(expectedDirect, actualDirect)
    }

    @Test
    fun getDirectionWhenDegBetween0and90() {
        val actualDirect = ru.neoanon.openweather.utils.getDirection(52.0)
        val expectedDirect = CardinalPoints.NE.name
        assertEquals(expectedDirect, actualDirect)
    }

    @Test
    fun getDirectionWhenDeg90() {
        val actualDirect = ru.neoanon.openweather.utils.getDirection(90.0)
        val expectedDirect = CardinalPoints.E.name
        assertEquals(expectedDirect, actualDirect)
    }

    @Test
    fun getDirectionWhenDegBetween90and180() {
        val actualDirect = ru.neoanon.openweather.utils.getDirection(123.0)
        val expectedDirect = CardinalPoints.SE.name
        assertEquals(expectedDirect, actualDirect)
    }

    @Test
    fun getDirectionWhenDeg180() {
        val actualDirect = ru.neoanon.openweather.utils.getDirection(180.0)
        val expectedDirect = CardinalPoints.S.name
        assertEquals(expectedDirect, actualDirect)
    }

    @Test
    fun getDirectionWhenDegBetween180and270() {
        val actualDirect = ru.neoanon.openweather.utils.getDirection(254.0)
        val expectedDirect = CardinalPoints.SW.name
        assertEquals(expectedDirect, actualDirect)
    }

    @Test
    fun getDirectionWhenDeg270() {
        val actualDirect = ru.neoanon.openweather.utils.getDirection(270.0)
        val expectedDirect = CardinalPoints.W.name
        assertEquals(expectedDirect, actualDirect)
    }

    @Test
    fun getDirectionWhenDegBetween270and360() {
        val actualDirect = ru.neoanon.openweather.utils.getDirection(320.0)
        val expectedDirect = CardinalPoints.NW.name
        assertEquals(expectedDirect, actualDirect)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getDirectionWhenBadDeg() {
        ru.neoanon.openweather.utils.getDirection(1000.0)
    }

    @Test
    fun hectoPascalToTorr() {
        val hectoPascal = 1021.75
        val actual = ru.neoanon.openweather.utils.hectoPascalToTorr(hectoPascal)
        val expected = 766
        assertEquals(expected, actual)
    }

    @Test
    fun buildTemperatureStrWithPlusCelsius() {
        val temp = 100
        val tempType = TempType.CELSIUS.id
        val actual = ru.neoanon.openweather.utils.buildTemperatureStr(temp, tempType)
        val expected = "+100 ℃"
        assertEquals(expected, actual)
    }

    @Test
    fun buildTemperatureStrWithMinusCelsius() {
        val temp = -100
        val tempType = TempType.CELSIUS.id
        val actual = ru.neoanon.openweather.utils.buildTemperatureStr(temp, tempType)
        val expected = "-100 ℃"
        assertEquals(expected, actual)
    }

    @Test
    fun buildTemperatureStrWithNullCelsius() {
        val temp = 0
        val tempType = TempType.CELSIUS.id
        val actual = ru.neoanon.openweather.utils.buildTemperatureStr(temp, tempType)
        val expected = "0 ℃"
        assertEquals(expected, actual)
    }

    @Test
    fun buildTemperatureStrWithPlusKelvin() {
        val temp = 100
        val tempType = TempType.KELVIN.id
        val actual = ru.neoanon.openweather.utils.buildTemperatureStr(temp, tempType)
        val expected = "+100 °K"
        assertEquals(expected, actual)
    }

    @Test
    fun buildTemperatureStrWithMinusKelvin() {
        val temp = -100
        val tempType = TempType.KELVIN.id
        val actual = ru.neoanon.openweather.utils.buildTemperatureStr(temp, tempType)
        val expected = "-100 °K"
        assertEquals(expected, actual)
    }

    @Test
    fun buildTemperatureStrWithNullKelvin() {
        val temp = 0
        val tempType = TempType.KELVIN.id
        val actual = ru.neoanon.openweather.utils.buildTemperatureStr(temp, tempType)
        val expected = "0 °K"
        assertEquals(expected, actual)
    }

    @Test
    fun buildTemperatureStrWithPlusWithoutTempType() {
        val temp = 100
        val actual = ru.neoanon.openweather.utils.buildTemperatureStr(temp, null)
        val expected = "+100 °"
        assertEquals(expected, actual)
    }

    @Test
    fun buildTemperatureStrWithMinusWithoutTempType() {
        val temp = -100
        val actual = ru.neoanon.openweather.utils.buildTemperatureStr(temp, null)
        val expected = "-100 °"
        assertEquals(expected, actual)
    }

    @Test
    fun buildTemperatureStrWithNullWithoutTempType() {
        val temp = 0
        val actual = ru.neoanon.openweather.utils.buildTemperatureStr(temp, null)
        val expected = "0 °"
        assertEquals(expected, actual)
    }
}
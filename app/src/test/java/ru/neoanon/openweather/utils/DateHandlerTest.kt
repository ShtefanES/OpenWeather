package ru.neoanon.openweather.utils

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import ru.neoanon.openweather.model.DateInfo
import java.util.*

/**
 * Created by eshtefan on  20.11.2018.
 */

class DateHandlerTest {

    private val timestamp = 1539165600000L//(GMT): Wednesday, 10 October 2018 г., 10:00:00
    private val timestampWeekend = 1540036800000L//(GMT): Saturday, 20 October 2018 г., 12:00:00

    private val timestampWithLocalOffset: Long by lazy {
        val tz = TimeZone.getDefault()
        val offset = tz.getOffset(timestamp)
        timestamp - offset
    }

    private val weekendTimestampWithLocalOffset: Long by lazy {
        val tz = TimeZone.getDefault()
        val offset = tz.getOffset(timestampWeekend)
        timestampWeekend - offset
    }

    private lateinit var dateHandler: DateHandler

    @Before
    fun setUp() {
        dateHandler = DateHandler(Locale("en"))
    }

    @Test
    fun getDayOfMonthAndWeek() {
        val actual = dateHandler.getDayOfMonthAndWeek(timestamp)
        val expected = """
            Wed
            10
        """.trimIndent()
        assertEquals(expected, actual)
    }

    @Test
    fun getLocalOffset() {
        val tz = TimeZone.getDefault()
        val expectedOffset = tz.getOffset(Calendar.getInstance().timeInMillis) / 1000 / 3600
        val actualOffset = dateHandler.getLocalOffset()
        assertEquals(expectedOffset, actualOffset)
    }

    @Test
    fun getHoursAndMinutes() {
        val actual = dateHandler.getHoursAndMinutes(timestampWithLocalOffset)
        val expected = "10:00"
        assertEquals(expected, actual)
    }

    @Test
    fun getDataInfo() {
        val expectedDayOfWeek = "Wednesday"
        val expectedDayOfMonth = "10 October"
        val expectedIsWeekend = false
        val expectedDateInfo = DateInfo(expectedDayOfWeek, expectedDayOfMonth, expectedIsWeekend)
        val actualDateInfo = dateHandler.getDataInfo(timestampWithLocalOffset)
        assertEquals(expectedDateInfo, actualDateInfo)
    }

    @Test
    fun getDataInfoWithWeekend() {
        val expectedDayOfWeek = "Saturday"
        val expectedDayOfMonth = "20 October"
        val expectedIsWeekend = true
        val expectedDateInfo = DateInfo(expectedDayOfWeek, expectedDayOfMonth, expectedIsWeekend)
        val actualDateInfo = dateHandler.getDataInfo(weekendTimestampWithLocalOffset)
        assertEquals(expectedDateInfo, actualDateInfo)
    }
}
package ru.neoanon.openweather.utils

import org.junit.Test

import org.junit.Assert.*
import ru.neoanon.openweather.data.source.local.db.hourlyforecast.HourlyForecast

/**
 * Created by eshtefan on  20.11.2018.
 */

class DbModelExtensionsKtTest {
    private val positiveOffset = 7
    private val negativeOffset = -5
    private val withoutOffset = 0


    @Test
    fun getNumberOfHourlyForecast() {
        val currentHour = 9
        val actualNumberOfForecasts = HourlyForecast.getNumberOfHourlyForecast(currentHour, positiveOffset)
        val expectedNumberOfForecast = 13
        assertEquals(expectedNumberOfForecast, actualNumberOfForecasts)
    }

    @Test
    fun getNumberOfHourlyForecastWithNegativeOffset() {
        val currentHour = 17
        val actualNumberOfForecasts = HourlyForecast.getNumberOfHourlyForecast(currentHour, negativeOffset)
        val expectedNumberOfForecast = 10
        assertEquals(expectedNumberOfForecast, actualNumberOfForecasts)
    }

    @Test
    fun getNumberOfHourlyForecastWithoutOffset() {
        val currentHour = 0
        val actualNumberOfForecasts = HourlyForecast.getNumberOfHourlyForecast(currentHour, withoutOffset)
        val expectedNumberOfForecast = 15
        assertEquals(expectedNumberOfForecast, actualNumberOfForecasts);
    }
}
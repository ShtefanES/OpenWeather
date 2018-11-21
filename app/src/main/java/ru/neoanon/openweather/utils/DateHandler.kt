package ru.neoanon.openweather.utils

import ru.neoanon.openweather.model.DateInfo
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 *Created by eshtefan on  13.11.2018.
 */

class DateHandler @Inject constructor(val locale: Locale) {

    fun getDayOfMonthAndWeek(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return SimpleDateFormat("EEE\nd", locale).format(calendar.time)
    }

    fun getCurrentTimestamp(): Long = Calendar.getInstance().timeInMillis

    fun getCurrentHour(): Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    fun getLocalOffset(): Int = Calendar.getInstance().timeZone.rawOffset / 3600000

    fun getHoursAndMinutes(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return SimpleDateFormat("HH:mm", locale).format(calendar.time)
    }

    fun getDataInfo(timestamp: Long): DateInfo {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return DateInfo(
            getDayOfWeek(locale, calendar),
            getDayOfMonth(locale, calendar),
            isWeekend(calendar)
        )
    }

    private fun getDayOfWeek(locale: Locale, calendar: Calendar): String =
        (SimpleDateFormat("EEEE", locale).format(calendar.time)).capitalize()


    private fun getDayOfMonth(locale: Locale, calendar: Calendar): String =
        (SimpleDateFormat("d MMMM", locale).format(calendar.time)).capitalize()


    private fun isWeekend(calendar: Calendar): Boolean {
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        return (day == 1 || day == 7)
    }
}
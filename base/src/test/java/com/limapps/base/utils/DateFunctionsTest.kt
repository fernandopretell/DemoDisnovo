package com.limapps.base.utils

import org.junit.Test
import java.util.*
import kotlin.test.assertTrue

class DateFunctionsTest {

    private val DATE_IN_SPECIAL_FORMAT = "2018-02-07 09:21:28.000000"
    private val DATE_IN_SPECIAL_FORMAT_2 = "1993-08-20 21:55:28.888888"

    @Test
    fun return0LWhenDateDoesNotMatchBaseFormat() {
        val timestamp = getTimestampFromDate(DATE_IN_SPECIAL_FORMAT, DATE_FORMAT_WITH_OR_SEPARATOR)

        assertTrue(timestamp == 0L)
    }

    @Test
    fun returnTimestampWhenDateMatches24HoursFormat() {
        val dateIn24HoursFormat = "2018-02-07 09:21:28"

        val timestamp = getTimestampFromDate(DATE_IN_SPECIAL_FORMAT, DATE_FORMAT_1_TO_24_HOURS)

        assertTrue(timestamp != 0L)
        assertTrue(getDateFromTimeStamp(timestamp, DATE_FORMAT_1_TO_24_HOURS) == dateIn24HoursFormat)
    }

    @Test
    fun returnTimestampWhenDateMatchesSpecialFormat() {
        val timestamp = getTimestampFromDate(DATE_IN_SPECIAL_FORMAT, DATE_FORMAT_1_TO_24_HOURS)
        val date = Date(timestamp)
        val calendar = Calendar.getInstance().apply { time = date }

        assertTrue(timestamp != 0L)
        assertTrue(calendar.get(Calendar.YEAR) == 2018)
        assertTrue(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY)
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) == 7)
        assertTrue(calendar.get(Calendar.MILLISECOND) == 0)
    }

    @Test
    fun returnTimestampWhenDateWithMoreMillisMatchesSpecialFormat() {
        val timestamp = getTimestampFromDate(DATE_IN_SPECIAL_FORMAT_2, DATE_FORMAT_1_TO_24_HOURS)
        val date = Date(timestamp)
        val calendar = Calendar.getInstance().apply { time = date }

        assertTrue(timestamp != 0L)
        assertTrue(calendar.get(Calendar.YEAR) == 1993)
        assertTrue(calendar.get(Calendar.MONTH) == Calendar.AUGUST)
        assertTrue(calendar.get(Calendar.DAY_OF_MONTH) == 20)
        assertTrue(calendar.get(Calendar.MILLISECOND) == 0)
    }
}
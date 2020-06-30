package com.limapps.base.models.store

import com.limapps.base.helpers.getClosestSchedule
import com.limapps.base.utils.serverDate
import org.junit.Test
import org.threeten.bp.format.DateTimeFormatter
import kotlin.random.Random
import kotlin.test.assertTrue

class StoreScheduleTest {

    @Test
    fun `Find closest store scheduled window in two unsorted schedules`() {

        val closestSchedule = createScheduleFromNow(1, 2)
        val lastSchedule = createScheduleFromNow(5, 3)

        val store = StoreDetail(storeId = 1, schedules = listOf(lastSchedule, closestSchedule))

        val schedule = store.getClosestSchedule()

        assertTrue { schedule.openTime == closestSchedule.openTime }
    }

    @Test
    fun `Find closest store scheduled window in sorted schedules`() {

        val closestSchedule = createScheduleFromNow(1, 2)
        val lastSchedule = createScheduleFromNow(5, 3)

        val store = StoreDetail(storeId = 1, schedules = listOf(closestSchedule, lastSchedule))

        val schedule = store.getClosestSchedule()

        assertTrue { schedule.openTime == closestSchedule.openTime }
    }

    @Test
    fun `Find closest store scheduled window in unsorted list of schedules`() {

        val closestSchedule = createScheduleFromNow(1, 2)

        val multipleSchedules = (0 until 10).map {
            // Random opening hours from now.
            val hoursFromNow = Random.nextInt(from = 3, until = 12)
            createScheduleFromNow(hoursFromNow, 4)
        }
        val listOfSchedules = multipleSchedules.plus(closestSchedule)

        val store = StoreDetail(storeId = 1, schedules = listOfSchedules)

        val schedule = store.getClosestSchedule()

        assertTrue { schedule.openTime == closestSchedule.openTime }
    }

    @Test
    fun `Find closest store scheduled window in unsorted list of schedules with past schedules`() {

        val closestSchedule = createScheduleFromNow(1, 2)

        val futureSchedules = (0 until 10).map {
            // Random opening hours from now.
            val hoursFromNow = Random.nextInt(from = 3, until = 12)
            createScheduleFromNow(hoursFromNow, 4)
        }

        val pastSchedules = (0 until 10).map {
            // Random opening hours before now.
            val hoursFromNow = Random.nextInt(from = 4, until = 12)
            createScheduleFromNow(hoursFromNow, 2, addHours = false)
        }
        val listOfSchedules = futureSchedules
                .plus(pastSchedules)
                .plus(closestSchedule)

        val store = StoreDetail(storeId = 1, schedules = listOfSchedules)

        val schedule = store.getClosestSchedule()

        assertTrue { schedule.openTime == closestSchedule.openTime }
    }


    private fun createScheduleFromNow(hoursFromNow: Int,
                                      windowInHours: Int,
                                      addHours: Boolean = true): StoreSchedule {

        val dateFormat = "HH:mm:ss"
        val dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)

        val localTime = serverDate().toLocalTime()


        val startLocalTime = if (addHours) {
            localTime.plusHours(hoursFromNow.toLong())
        } else {
            localTime.minusHours(hoursFromNow.toLong())
        }
        val openTime = startLocalTime.format(dateTimeFormatter)


        val closeLocalTime = startLocalTime.plusHours(windowInHours.toLong())
        val closeTime = closeLocalTime.format(dateTimeFormatter)

        return StoreSchedule(openTime = openTime, closeTime = closeTime)
    }
}
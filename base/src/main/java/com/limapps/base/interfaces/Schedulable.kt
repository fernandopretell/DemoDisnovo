package com.limapps.base.interfaces

import com.limapps.base.models.store.StoreSchedule
import com.limapps.base.utils.serverDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

interface Schedulable {

    val schedules: List<StoreSchedule>

    fun isOpen(timeToCheck: LocalDateTime? = null): Boolean {
        val currentTime = timeToCheck?.toLocalTime() ?: serverDate().toLocalTime()
        return if (schedules.isEmpty()) {
            true
        } else {
            schedules.any {
                if (it.openTime.isNullOrEmpty() || it.closeTime.isNullOrEmpty()) {
                    true
                } else {
                    val openTime = LocalTime.parse(it.openTime)
                    val closeTime = LocalTime.parse(it.closeTime)
                    currentTime.isAfter(openTime) && currentTime.isBefore(closeTime)
                }
            }
        }
    }
}
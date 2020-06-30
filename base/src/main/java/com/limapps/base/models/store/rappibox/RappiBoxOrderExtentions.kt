package com.limapps.base.models.store.rappibox

import com.limapps.base.utils.getHourFromDateFormat
import com.limapps.base.utils.getHourScheduleFromDateFormat

fun RappiBoxOrder.startTimeSlot() = getHourFromDateFormat(scheduleInfo.openTime)

fun RappiBoxOrder.endTimeSlot() = getHourFromDateFormat(scheduleInfo.closeTime)

fun RappiBoxOrder.slotWindow() =
        "${getHourScheduleFromDateFormat(scheduleInfo.openTime)} - ${getHourScheduleFromDateFormat(scheduleInfo.closeTime)}"
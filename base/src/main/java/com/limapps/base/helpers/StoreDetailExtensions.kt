package com.limapps.base.helpers

import com.limapps.base.R
import com.limapps.base.models.SearchModel
import com.limapps.base.models.store.DeliveryMethodTypes
import com.limapps.base.models.store.StoreDetail
import com.limapps.base.models.store.StoreSchedule
import com.limapps.base.others.ResourcesProvider
import com.limapps.base.utils.checkWithServerTime
import com.limapps.base.utils.serverTimeIsBeforeThan
import org.threeten.bp.LocalTime

fun StoreDetail.isStoreOpen(): Boolean = checkSchedules(schedules)

fun SearchModel.SearchStore.isStoreOpen(): Boolean = checkSchedules(schedules)

fun StoreDetail.getCurrentEta(etaRange: Int, defaultEta: Int): String = when {
    !hasPromise -> getRestaurantEta(this.etaValue, this.eta, etaRange, defaultEta)
    else -> eta
}

fun StoreDetail.getCurrentEta(): String = eta.replace(" ", "")

fun StoreDetail.getClosestSchedule(): StoreSchedule {
    val sortedSchedules = schedules.sortedBy { LocalTime.parse(it.openTime) }
    val firstSchedule = sortedSchedules.first()
    return if (nextScheduleHaveNotStarted(firstSchedule)) {
        firstSchedule
    } else {
        sortedSchedules
                .zipWithNext()
                .firstOrNull { (firstSchedule, secondSchedule) ->
                    previousScheduleHasFinished(firstSchedule) &&
                            nextScheduleHaveNotStarted(secondSchedule)
                }
                ?.second
                ?: sortedSchedules.first()
    }
}

private fun previousScheduleHasFinished(firstSchedule: StoreSchedule): Boolean {
    return !serverTimeIsBeforeThan(firstSchedule.closeTime.orEmpty())
}

private fun nextScheduleHaveNotStarted(secondSchedule: StoreSchedule): Boolean {
    return serverTimeIsBeforeThan(secondSchedule.openTime.orEmpty())
}

fun getRestaurantEta(etaValue: Int, eta: String, etaRange: Int, defaultEta: Int): String {
    val minRange = etaValue - etaRange
    return if (etaRange > 0 && minRange > 0) {
        ResourcesProvider.getString(R.string.base_item_eta, minRange, etaValue + etaRange)
    } else if (etaValue == 0) {
        ResourcesProvider.getString(R.string.base_simple_eta, defaultEta)
    } else {
        eta
    }
}

private fun checkSchedules(schedules: List<StoreSchedule>): Boolean {
    return schedules.any { checkWithServerTime(it.openTime.orEmpty(), it.closeTime.orEmpty()) }
}

private fun hasMethod(storeDetail: StoreDetail, method: DeliveryMethodTypes): Boolean {
    storeDetail.deliveryMethods.forEach { value ->
        if (value == method.value) return true
    }
    return false
}

fun StoreDetail.hasMoreThanOneMethod() = this.deliveryMethods.count() > 1

fun StoreDetail.hasDeliveryMethod() = hasMethod(this, DeliveryMethodTypes.DELIVERY)

fun StoreDetail.hasPickupMethod() = hasMethod(this, DeliveryMethodTypes.PICKUP)
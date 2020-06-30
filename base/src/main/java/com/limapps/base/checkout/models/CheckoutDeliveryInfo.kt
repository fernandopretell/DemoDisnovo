package com.limapps.base.checkout.models

import android.os.Parcelable
import com.limapps.base.models.Saturation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckoutDeliveryInfo(
        val storeEta: String? = null,
        val scheduleInfo: ScheduleInformation? = null,
        val promise: CheckoutPromise? = null,
        val serviceFee: Double? = null,
        var orderPlacedParameters: List<ParameterModel> = listOf(),
        val saturation: Saturation? = null,
        val shouldShowFidelityId: Boolean? = false,
        val postOrderServiceAction: String? = null
) : Parcelable
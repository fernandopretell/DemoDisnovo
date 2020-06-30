package com.limapps.base.support.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderInfoSupport(
        var orderId: String,
        val storeType: String,
        var productList: List<SupportInfoProduct>
) : Parcelable
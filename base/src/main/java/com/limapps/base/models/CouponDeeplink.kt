package com.limapps.base.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CouponDeeplink(
        @SerializedName("store_type") val storeType: String? = "",
        @SerializedName("brand_name") val brandName: String? = "",
        @SerializedName("product_id") val productId: List<String>? = null
) : Parcelable
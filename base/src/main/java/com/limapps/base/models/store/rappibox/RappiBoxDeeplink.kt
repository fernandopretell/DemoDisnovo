package com.limapps.base.models.store.rappibox

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RappiBoxDeeplink(
        @SerializedName("url") val url: String,
        @SerializedName("require_user") val requireUser: Boolean,
        @SerializedName("store_type") val storeType: String,
        @SerializedName("need_coupon") val needCoupon: Boolean,
        @SerializedName("show_loaders") val showCustomLoaders: Boolean,
        @SerializedName("show_refresh") val showRefresh: Boolean,
        @SerializedName("show_description") val showDescription: Boolean,
        @SerializedName("native_checkout") val nativeCheckout: Boolean
) : Parcelable
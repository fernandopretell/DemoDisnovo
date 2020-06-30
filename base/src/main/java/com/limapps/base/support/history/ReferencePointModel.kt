package com.limapps.base.support.history

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReferencePointModel(@SerializedName("order_id") val orderId: Long = 0,
                               @SerializedName("lat") val lat: Double = 0.0,
                               @SerializedName("lng") val lng: Double = 0.0,
                               @SerializedName("google_place_id") val placeId: String? = "",
                               @SerializedName("name") val name: String = "",
                               @SerializedName("address") val address: String = "") : Parcelable
package com.limapps.base.support.history

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class CalculatedInformationModel(
        @SerializedName("order_id") val orderId: String = "",
        @SerializedName("store_id") val storeId: String? = "",
        @SerializedName("store_type") val storeType: String = "",
        @SerializedName("reference_point_lat") val lat: Double = 0.0,
        @SerializedName("reference_point_lng") val lng: Double = 0.0) : Parcelable
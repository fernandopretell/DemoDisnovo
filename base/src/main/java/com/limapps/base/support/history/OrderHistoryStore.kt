package com.limapps.base.support.history

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderHistoryStore(@SerializedName("id") val storeId: Int,
                             @SerializedName("name") val name: String,
                             @SerializedName("lat") val latitude: Double,
                             @SerializedName("lng") val longitude: Double,
                             @SerializedName("logo") val logo: String? = null,
                             @SerializedName("store_type") val storeType: String = "",
                             @SerializedName("store_type_image") val storeTypeImage: String = "",
                             @SerializedName("image") val image: String = "") : Parcelable
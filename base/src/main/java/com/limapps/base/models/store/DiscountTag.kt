package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiscountTag(@SerializedName("type") val type: String = "",
                       @SerializedName("tag") val tag: String = "",
                       @SerializedName("color") val color: String = "",
                       @SerializedName("title") val title: String = "",
                       @SerializedName("description") val description: String = "",
                       @SerializedName("message") val message: String = "",
                       @SerializedName("url") val url: String = "",
                       @SerializedName("value") val value: Double = 0.0) : Parcelable
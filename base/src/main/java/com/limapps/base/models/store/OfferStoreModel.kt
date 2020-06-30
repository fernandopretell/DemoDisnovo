package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferStoreModel(@SerializedName("tag") val tag: String? = "",
                           @SerializedName("type") val type: String? = "",
                           @SerializedName("title") val title: String? = "",
                           @SerializedName("value") val value: Double,
                           @SerializedName("label") val label: OfferLabelModel?,
                           @SerializedName("message") val message: String? = "",
                           @SerializedName("description") val description: String? = "",
                           @SerializedName("url") val url: String? = "") : Parcelable
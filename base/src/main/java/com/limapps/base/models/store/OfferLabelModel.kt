package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferLabelModel(@SerializedName("text_color") val textColor: String,
                           @SerializedName("background_color") val backgroundColor: String,
                           @SerializedName("alpha") val alpha: Float = 0.2f
) : Parcelable
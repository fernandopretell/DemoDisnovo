package com.limapps.base.support.history

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderHistoryAddress(@SerializedName("lat") val latitude: Double,
                               @SerializedName("lng") val longitude: Double) : Parcelable
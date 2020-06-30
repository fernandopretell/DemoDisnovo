package com.limapps.base.location

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(@SerializedName (value = "latitude")
                    val latitude: Double = 0.0,
                    @SerializedName (value = "longitude")
                    val longitude: Double = 0.0):Parcelable
package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreSchedule(@SerializedName("open_time") val openTime: String?,
                         @SerializedName("close_time") val closeTime: String?) : Parcelable
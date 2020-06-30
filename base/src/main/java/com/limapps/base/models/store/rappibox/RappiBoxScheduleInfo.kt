package com.limapps.base.models.store.rappibox

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RappiBoxScheduleInfo(
        @SerializedName("open_time") val openTime: String,
        @SerializedName("close_time") val closeTime: String
) : Parcelable
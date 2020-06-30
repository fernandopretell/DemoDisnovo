package com.limapps.base.support.history

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderHistoryWhim(@SerializedName("name") val whimName: String = "",
                            @SerializedName("url_photo") val whimImage: String = "") : Parcelable
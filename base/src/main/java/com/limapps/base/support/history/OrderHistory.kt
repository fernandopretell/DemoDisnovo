package com.limapps.base.support.history

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderHistory(@SerializedName("data") val orderHistoryItems: List<OrderHistoryItem> = emptyList(),
                        @SerializedName("total") val total: Int = 0) : Parcelable
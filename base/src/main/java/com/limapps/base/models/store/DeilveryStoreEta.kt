package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeliveryEta(@SerializedName("eta_type") val etaType: EtaType? = null,
                       @SerializedName("date") val date: String,
                       @SerializedName("eta_unit") val etaUnit: EtaUnit? = null ,
                       @SerializedName("start_time") val startTime: String,
                       @SerializedName("end_time") val endTime: String,
                       @SerializedName("price") val price: String?) : Parcelable


enum class EtaUnit {
    M, H, HR
}

enum class EtaType  {
    @SerializedName("today")
    TODAY,
    @SerializedName("tomorrow")
    TOMORROW,
    @SerializedName("after_tomorrow")
    AFTER_TOMORROW
}
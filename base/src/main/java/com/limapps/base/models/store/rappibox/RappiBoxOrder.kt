package com.limapps.base.models.store.rappibox

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RappiBoxOrder(
        @SerializedName("products") val products: List<RappiBoxProduct>,
        @SerializedName("schedule_info") var scheduleInfo: RappiBoxScheduleInfo,
        @SerializedName("store_type") val storeType: String
) : Parcelable {

    companion object {
        fun fromJsonString(jsonString: String) = Gson().fromJson(jsonString, RappiBoxOrder::class.java)
    }
}
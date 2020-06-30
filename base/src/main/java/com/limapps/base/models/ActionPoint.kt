package com.limapps.base.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActionPoint(@SerializedName("address")
                       val address: String,
                       @SerializedName("action")
                       val action: String = "",
                       @SerializedName("google_place_id")
                       val googlePlaceIdString: String = "",
                       @SerializedName("index")
                       val index: Int,
                       @SerializedName("lat")
                       val lat: Double,
                       @SerializedName("lng")
                       val lng: Double,
                       @SerializedName("price_rule_id")
                       val priceRule: Int? = null) : Parcelable
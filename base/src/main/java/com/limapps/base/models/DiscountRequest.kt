package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class DiscountRequest(@SerializedName("lat") val latitude: Double,
                           @SerializedName("lng") val longitude: Double,
                           @SerializedName("store_type") val storeType: String)
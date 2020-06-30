package com.limapps.base.dynamicrate.v1

import com.google.gson.annotations.SerializedName

data class Charge(
        @SerializedName("charge_type") var chargeType: String? = null,
        @SerializedName("id") var id: String? = null,
        @SerializedName("rate") var rate: String? = null,
        @SerializedName("show") var isShow: Boolean = false,
        @SerializedName("value") var value: Double = 0.toDouble()
)

package com.limapps.base.models.loyalty

import com.google.gson.annotations.SerializedName

data class Benefit(
        @SerializedName("coupon")
        val coupon: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: String
)
package com.limapps.base.models.loyalty

import com.google.gson.annotations.SerializedName

data class Loyalty(
        @SerializedName("active")
        val active: Boolean,
        @SerializedName("benefits")
        val benefits: List<Benefit>,
        @SerializedName("description")
        val description: String,
        @SerializedName("end_color")
        val endColor: String?,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String?,
        @SerializedName("total_points")
        val userPoints: Int,
        @SerializedName("limit_lower")
        val limitLower: Int,
        @SerializedName("limit_higher")
        val limitHigher: Int,
        @SerializedName("name")
        val name: String?,
        @SerializedName("period")
        val period: Period?,
        @SerializedName("start_color")
        val startColor: String?,
        @SerializedName("type")
        val type: String
)
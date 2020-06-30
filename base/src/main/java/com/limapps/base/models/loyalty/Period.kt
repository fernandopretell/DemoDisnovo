package com.limapps.base.models.loyalty

import com.google.gson.annotations.SerializedName

data class Period(
        @SerializedName("active")
        val active: Boolean,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("ends_at")
        val endsAt: String,
        @SerializedName("id")
        val id: Long,
        @SerializedName("init_at")
        val initAt: String,

        val totalPoints: Double? = null
)

fun Period.plusPoints(totalPoints: Double): Period {
    return Period(this.active, this.createdAt, this.endsAt, this.id, this.initAt, totalPoints)
}
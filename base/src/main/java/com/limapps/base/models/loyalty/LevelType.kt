package com.limapps.base.models.loyalty

import com.google.gson.annotations.SerializedName

enum class LevelType(val type: String) {
    @SerializedName("bronze")
    BRONZE("bronze"),
    @SerializedName("silver")
    SILVER("silver"),
    @SerializedName("gold")
    GOLD("gold"),
    @SerializedName("diamond")
    DIAMOND("diamond");

    companion object {
        private val map = LevelType.values().associateBy(LevelType::type)

        fun fromString(method: String) = map[method.toLowerCase()] ?: BRONZE
    }
}
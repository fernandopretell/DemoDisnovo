package com.limapps.base.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PersistenceTopping(
        @SerializedName("id") val id: String,
        @SerializedName("description") val description: String = "",
        @SerializedName("price") val price: Double = 0.0,
        val isChecked: Boolean = false,
        val units: Int? = 0,
        @SerializedName("index") val index: Int = 0,
        @SerializedName("topping_category_id") val toppingCategoryId: Int,
        @SerializedName("max_limit") val maxLimit: Int? = 1,
        @SerializedName("is_available") val isAvailable: Boolean? = true)
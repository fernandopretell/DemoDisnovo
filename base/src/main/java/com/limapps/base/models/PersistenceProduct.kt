package com.limapps.base.models

import com.google.gson.annotations.SerializedName
import com.limapps.base.models.Topping

data class PersistenceProduct(
        @SerializedName("id") val id: String,
        @SerializedName("quantity") val quantity: Int,
        @SerializedName("has_comments") val hasComments: Boolean,
        @SerializedName("comment") val comment: String,
        @SerializedName("toppings") val toppings: List<PersistenceTopping>,
        @SerializedName("sale_type") val saleType: String,
        @SerializedName("category") val category: String
)
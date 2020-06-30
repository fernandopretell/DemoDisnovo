package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class MissingProduct(
        @SerializedName("product_id") val id: String,
        val name: String,
        val quantity: String,
        val saleType: String,
        val price: Double,
        val imageUrl: String,
        val totalPrice: Double,
        var checked: Boolean = false)
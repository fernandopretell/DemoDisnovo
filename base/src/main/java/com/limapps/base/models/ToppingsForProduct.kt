package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class ToppingsForProduct(
        @SerializedName(value = "data", alternate = ["categories"]) val toppingsCategories: List<ToppingCategory> = emptyList(),
        @SerializedName(value = "sort") val sort: String? = "default",
        @SerializedName(value = "style") val style: String? = "default")
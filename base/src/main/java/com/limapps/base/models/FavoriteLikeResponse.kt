package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class FavoriteLikeResponse(
        @SerializedName("quantity_orders") val quantityOrders: String = "",
        @SerializedName("store_id") val storeId: String = "",
        @SerializedName("is_selected") val isSelected: Boolean = false
)
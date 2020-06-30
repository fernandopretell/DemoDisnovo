package com.limapps.base.support.history

import com.google.gson.annotations.SerializedName
import com.limapps.base.support.history.OrderHistoryItem

data class OrderHistoryResponse(@SerializedName("current_page") val currentPage: Int = 0,
                                @SerializedName("total") val total: Int = 0,
                                @SerializedName("data") val orderHistoryItems: List<OrderHistoryItem> = emptyList())
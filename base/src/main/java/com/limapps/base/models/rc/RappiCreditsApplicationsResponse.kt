package com.limapps.base.models.rc

import com.google.gson.annotations.SerializedName

data class RappiCreditsApplicationsResponse(
        @SerializedName("page") val page: Int,
        @SerializedName("page_available") val pageAvailable: Int,
        @SerializedName("page_item_count") val pageItemCount: Int,
        @SerializedName("total") val total: Double,
        @SerializedName("total_shipping") val totalShipping: Double,
        @SerializedName("rappi_credit_transactions") val rcTransactions: List<RappiCreditTransactionModel>
)

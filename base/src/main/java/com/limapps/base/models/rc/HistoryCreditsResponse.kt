package com.limapps.base.models.rc

import com.google.gson.annotations.SerializedName

data class HistoryCreditsResponse(
        @SerializedName("rappi_credit_transactions")
        val transactions: List<RappiCreditTransactionModel>
)
package com.limapps.base.creditcards.models

import com.google.gson.annotations.SerializedName

data class CardVerificationResponse(@SerializedName("success") val success: Boolean,
                                    @SerializedName("can_retry") val canRetry: Boolean)

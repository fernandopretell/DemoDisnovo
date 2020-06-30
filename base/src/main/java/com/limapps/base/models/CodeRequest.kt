package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class CodeRequest(
        @SerializedName("phone") val phone: String?,
        @SerializedName("country_code") val countryCode: String?,
        @SerializedName("via") val via: String
)
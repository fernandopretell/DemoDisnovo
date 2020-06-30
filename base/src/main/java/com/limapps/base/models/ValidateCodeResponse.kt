package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class ValidateCodeResponse(
        @SerializedName("message") val message: String?,
        @SerializedName("success") val success: Boolean)
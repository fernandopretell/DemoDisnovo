package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class ResendServiceRequest(
        @SerializedName("key")
        var key: String? = null
)

data class ResendServiceResponse(
        @SerializedName("success")
        var success: Boolean? = null,

        @SerializedName("error")
        var error: TwoFactorError? = null
)

class TwoFactorError(message: String? = null, val email: String? = null) : Throwable(message)
package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class RequestCodeResponse(
        @SerializedName("carrier") val carrier: String?,
        @SerializedName("is_cellphone") val isCellphone: Boolean?,
        @SerializedName("message") val message: String?,
        @SerializedName("seconds_to_expire") val secondsToExpire: Long?,
        @SerializedName("uuid") val uuid: String?,
        @SerializedName("success") val success: Boolean)


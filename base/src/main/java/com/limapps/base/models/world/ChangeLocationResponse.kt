package com.limapps.base.models.world

import com.google.gson.annotations.SerializedName

class ChangeLocationResponse(
        @SerializedName("token_type")
        var tokenType: String,
        @SerializedName("refresh_token")
        var refreshToken: String,
        @SerializedName("access_token")
        var accessToken: String) {

    fun hasValidCredentials(): Boolean {
        return accessToken.isNotEmpty() && refreshToken.isNotEmpty()
    }

    constructor() : this("", "", "")
}
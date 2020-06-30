package com.limapps.base.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.limapps.base.rest.constants.HttpConstants

data class RefreshTokenRequest(@SerializedName("refresh_token")
                               @Expose
                               val refreshToken: String,
                               @SerializedName("scope")
                               @Expose
                               val scope: String = "all")
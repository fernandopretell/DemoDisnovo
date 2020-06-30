package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class FirebaseToken(
        @SerializedName("access_token") val accessToken: String = ""
)
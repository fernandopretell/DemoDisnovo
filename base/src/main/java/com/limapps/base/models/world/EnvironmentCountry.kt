package com.limapps.base.models.world

import com.google.gson.annotations.SerializedName
import com.limapps.base.models.ServerConfig

class EnvironmentCountry(
        @SerializedName("country")
        val country: String,
        @SerializedName("environment")
        val environment: ServerConfig
)
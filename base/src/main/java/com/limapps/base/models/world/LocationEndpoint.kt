package com.limapps.base.models.world

import com.google.gson.annotations.SerializedName

class LocationEndpoint(
        @SerializedName("country")
        var country: String,
        @SerializedName("environment")
        var environment: EnvironmentCountry
)
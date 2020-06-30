package com.limapps.base.models.world

import com.google.gson.annotations.SerializedName
import com.limapps.base.models.ServerConfig

class LocationChangeCountry(
        @SerializedName("location_origin")
        var origin: EnvironmentCountry,
        @SerializedName("location_destination")
        var destination: EnvironmentCountry,
        @SerializedName("has_change_country")
        var hasChangeCountry: Boolean
) {

    val originUrlServer: String
        get() = origin.environment.server

    val resolveEnvironment: ServerConfig
        get() = if (hasChangeCountry) destination.environment else origin.environment

    val resolveNewServer: String
        get() = destination.environment.services
}
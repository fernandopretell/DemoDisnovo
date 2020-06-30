package com.limapps.base.models.world

import com.google.gson.annotations.SerializedName

class ChangeLocationRequestV2(latitude: String, longitude: String) {

    @SerializedName("lat")
    val latitude: String = latitude
    @SerializedName("lng")
    val longitude: String = longitude
}
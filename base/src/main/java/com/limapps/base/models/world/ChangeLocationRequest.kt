package com.limapps.base.models.world

import com.google.gson.annotations.SerializedName
import com.limapps.base.models.Address

class ChangeLocationRequest(currentAddress: Address, newAddress: Address, @SerializedName("email") val email: String) {

    @SerializedName("lat_origin")
    val latitude: Double = currentAddress.latitude
    @SerializedName("lng_origin")
    val longitude: Double = currentAddress.longitude
    @SerializedName("lat_destination")
    val latDestination: Double = newAddress.latitude
    @SerializedName("lng_destination")
    val lonDestination: Double = newAddress.longitude
}
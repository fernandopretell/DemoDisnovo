package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class SuggestedAddressResponse(
        @SerializedName("address") val address: Address? = null,
        @SerializedName("notify_farness") val notifyFarness: Boolean = false,
        @SerializedName("show_addresses") val showAddresses: Boolean = true
)
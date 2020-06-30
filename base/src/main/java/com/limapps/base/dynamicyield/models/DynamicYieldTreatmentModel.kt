package com.limapps.base.dynamicyield.models

import com.google.gson.annotations.SerializedName

data class DynamicYieldTreatmentModel (
    @SerializedName("is_available") val isAvailable: Boolean = false
)
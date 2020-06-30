package com.limapps.base.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Saturation(
        @SerializedName("eta") val eta: SaturationActionValue,
        @SerializedName("price") val price: SaturationActionValue,
        @SerializedName("store_id") val storeId: Int,
        @SerializedName("switches") val switches: Switches,
        @SerializedName("weight") val weight: SaturationActionValue,
        @SerializedName("saturations") val parameters: SaturationParameters? = null,
        @SerializedName("distance") val distance: Int? = null,
        @SerializedName("cooking_time") val cookingTime: Int
) : Parcelable

@Parcelize
data class Switches(
        @SerializedName("checkout_confirmation")
        val checkoutConfirmation: Int,
        @SerializedName("high_demand")
        val highDemand: Int,
        @SerializedName("cash")
        val cash: Int
) : Parcelable

@Parcelize
data class SaturationActionValue(
        @SerializedName("action")
        val action: Int,
        @SerializedName("value")
        val value: Double
) : Parcelable


@Parcelize
data class SaturationParameters(
        @SerializedName("geographical")
        val geo: String,
        @SerializedName("store")
        val store: String
) : Parcelable
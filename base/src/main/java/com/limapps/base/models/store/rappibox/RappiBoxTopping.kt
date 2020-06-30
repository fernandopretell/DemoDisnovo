package com.limapps.base.models.store.rappibox

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RappiBoxTopping(
        @SerializedName("id") val id: String,
        @SerializedName("quantity") val quantity: Int,
        @SerializedName("price") val price: Double
) : Parcelable
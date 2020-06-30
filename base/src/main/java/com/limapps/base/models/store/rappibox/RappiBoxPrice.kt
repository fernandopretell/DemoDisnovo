package com.limapps.base.models.store.rappibox

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RappiBoxPrice(
        @SerializedName("color") val color: String,
        @SerializedName("tag") val tag: String
) : Parcelable
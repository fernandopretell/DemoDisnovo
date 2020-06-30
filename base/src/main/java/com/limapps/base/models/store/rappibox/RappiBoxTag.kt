package com.limapps.base.models.store.rappibox

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RappiBoxTag(
        @SerializedName("name") val name: String,
        @SerializedName("index") val index: Int,
        @SerializedName("icon") val icon: String,
        @SerializedName("deeplink") val deeplink: RappiBoxDeeplink
) : Parcelable
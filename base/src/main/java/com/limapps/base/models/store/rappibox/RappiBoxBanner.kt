package com.limapps.base.models.store.rappibox

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RappiBoxBanner(
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("image") val imageUrl: String,
        @SerializedName("index") val index: Int,
        @SerializedName("appearance") val appearance: RabbiBoxAppearance,
        @SerializedName("deeplink") val deeplink: RappiBoxDeeplink
) : Parcelable
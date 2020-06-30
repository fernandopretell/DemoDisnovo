package com.limapps.base.models.store.rappibox

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RappiBox(
        @SerializedName("tag") val tag: RappiBoxTag,
        @SerializedName("banner") val banner: RappiBoxBanner
) : Parcelable


package com.limapps.base.models.store.rappibox

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RabbiBoxAppearance(@SerializedName("price") val price: RappiBoxPrice) : Parcelable
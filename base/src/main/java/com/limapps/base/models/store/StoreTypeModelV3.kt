package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreTypeModelV3(@SerializedName("name") val name: String?,
                            @SerializedName("description") val description: String?,
                            @SerializedName("store_type") val storeType: String?,
                            @SerializedName("scheduled") val scheduled: Boolean = false) : Parcelable

fun StoreTypeModelV2.toV3(): StoreTypeModelV3 {
    return StoreTypeModelV3(name, description, storeType, scheduled)
}
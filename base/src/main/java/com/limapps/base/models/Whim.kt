package com.limapps.base.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Whim(@SerializedName("whim") val name: String?,
           @SerializedName("where") val place: String?,
           @SerializedName("place_info") val placeInfo: PlaceInfo?,
           @SerializedName("store_id") val storeId: String?,
           @SerializedName("surcharge") val surcharge: Double,
           @SerializedName("whim_pictures_urls") val pictures: List<String> = emptyList(),
           @Transient val estimatedCost : Int? = null,
           @Transient val productId : String? = null
) : Parcelable
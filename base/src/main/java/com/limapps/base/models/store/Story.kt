package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Story(
        @SerializedName("id") val id: Int,
        @SerializedName("image") val image: String,
        @SerializedName("duration") val duration: Long,
        @SerializedName("deep_link") val deepLink: DeepLink
): Parcelable {

    @Parcelize
    data class DeepLink(
            @SerializedName("brand_name") val brandName: String? = null,
            @SerializedName("store_id") val storeId: Int? = null,
            @SerializedName("product_id") val productId: Long? = null,
            @SerializedName("stores") val stores: List<String>? = null
    ): Parcelable
}
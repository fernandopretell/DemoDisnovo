package com.limapps.base.models.store

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating(@SerializedName("store_id") val storeId: Int = 0,
                  @SerializedName("score") val score: Double = 0.0,
                  @SerializedName("total_reviews") val totalReviews: Int = 0) : Parcelable {

}
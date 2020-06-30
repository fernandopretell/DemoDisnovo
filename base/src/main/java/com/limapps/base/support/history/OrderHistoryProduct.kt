package com.limapps.base.support.history

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderHistoryProduct(@SerializedName("id") val productId: String = "",
                               @SerializedName("name") val productName: String = "",
                               @SerializedName("image") val productImage: String = "",
                               @SerializedName("units") val productUnits: Int = 1,
                               @SerializedName("sale_type") val productSaleType: String = "",
                               @SerializedName("store_id") val storeId: Int = 0,
                               @SerializedName("price") val price: Double = 0.0,
                               @SerializedName("step_quantity_in_grams") val stepQuantityInGrams: Int = 0,
                               @SerializedName("min_quantity_in_grams") val minQuantityInGrams: Int = 0) : Parcelable
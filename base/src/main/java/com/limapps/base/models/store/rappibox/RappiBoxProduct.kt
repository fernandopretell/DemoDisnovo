package com.limapps.base.models.store.rappibox

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RappiBoxProduct(
        @SerializedName("id") val id: String,
        @SerializedName("quantity") val quantity: Int,
        @SerializedName("price") val price: Double,
        @SerializedName("real_price") val realPrice: Double,
        @SerializedName("store_id") val storeId: String,
        @SerializedName("toppings") val topping: List<RappiBoxTopping>
) : Parcelable
package com.limapps.base.models

import android.os.Parcelable
import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

const val PRODUCTS_DISCOUNT = 1
const val SHIPMENT_DISCOUNT = 5
const val GENERAL_DISCOUNT = 6


@Parcelize
data class Offer(@SerializedName("id") val id: String = "",
                 @SerializedName("type") val type: Int = 0,
                 @SerializedName("value") val valueString: String? = "0",
                 @SerializedName("max") val max: Int = 1,
                 @SerializedName("description") val description: String = "",
                 @SerializedName("starts_on") val startDate: String = "",
                 @SerializedName("ends_on") val endDate: String = "",
                 @SerializedName("code") val code: String? = null,
                 @SerializedName("coupon_id") val couponId: Long = 0,
                 @SerializedName("products") val products: List<Product> = emptyList()) : Parcelable

fun Offer.getProductList(): List<CouponProduct> {
    return products.map {
        CouponProduct(it.id, valueString ?: "0", it.price, it.quantityPresentation ?: "")
    }
}

fun Offer.getValue(): Double {
    var value = 0.0

    if (!TextUtils.isEmpty(valueString)) {
        try {
            value = java.lang.Double.valueOf(valueString.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return value
}

fun Offer.belongsToCoupon(): Boolean = couponId > 0
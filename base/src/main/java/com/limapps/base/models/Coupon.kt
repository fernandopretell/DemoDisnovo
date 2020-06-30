package com.limapps.base.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.base.others.StoreTypeConstants
import com.limapps.base.utils.priceToString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coupon(@SerializedName("id") val id: Long = 0,
                  @SerializedName("starts_on") val startDate: String? = "",
                  @SerializedName("ends_on") val endDate: String? = "",
                  @SerializedName("code") val code: String = "",
                  @SerializedName("friendly_title") val friendlyTitle: String = "",
                  @SerializedName("redeem_message") val redeemMessage: String = "",
                  @SerializedName("coupon_message") val couponMessage: String? = "",
                  @SerializedName("offers") val offers: List<Offer> = emptyList(),
                  @SerializedName("coupon_codes") val couponsCodes: List<CouponCode> = emptyList(),
                  @SerializedName("image") val image: String = "",
                  @SerializedName("coupon_type") val couponType: String? = "",
                  @SerializedName("coupon_code_id") val couponCodeId: String? = "",
                  @SerializedName("referred_coupon_type_id") val referredCouponTypeId: Int? = 0,
                  @SerializedName("referrer_user_id") val referrerUserId: String? = "",
                  @SerializedName("coupon_conditions") val couponConditions: List<CouponCondition> = listOf(),
                  @SerializedName("deeplink") val deepLink: CouponDeeplink? = null,
                  @SerializedName("payments_available") val paymentsAvailable: List<String>? = null,
                  @SerializedName("store_type") val storeType: String? = null,
                  @SerializedName("store_type_id") val storeTypeId: String? = null,
                  @SerializedName("quantity") val totalUses: Int = 1,
                  @SerializedName("left_quantity") val timesLeft: Int = 0
) : Parcelable

fun Coupon.getCouponCode(): String? {
    return couponsCodes.firstOrNull()?.code
}


fun Coupon.getPricePrime(): String {
    val selectedOffer: Offer? = offers.firstOrNull { offer ->
        offer.products.isNotEmpty()
    }

    val product = selectedOffer?.products?.firstOrNull()

    var price = 0.0
    product?.let {
        price += it.price * (100 - (selectedOffer?.valueString?.toDouble() ?: 0.0)) / 100
    }

    return price.priceToString()


}

fun Coupon.getOfferByType(type: Int): Offer? {
    return this.offers.firstOrNull { it.type == type }
}


/**
 * return null if no prime product was found
 */
fun Coupon.getPrimeProduct(): Product? {
    val offer = offers.firstOrNull { it.type == PRODUCTS_DISCOUNT }
    return offer?.products?.firstOrNull()
}


fun Coupon.isPrime() : Boolean {
    return storeType == StoreTypeConstants.RAPPI_PRIME
}
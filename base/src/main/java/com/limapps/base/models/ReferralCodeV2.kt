package com.limapps.base.models

import com.google.gson.annotations.SerializedName

const val REFERRAL_EARN_TYPE = "12"

const val REFERRAL_GIFT_TYPE = "13"

data class ReferredCodeV3(
        @SerializedName("coupon")
        val coupon: ReferredCodeV2,

        @SerializedName("tier")
        val tier: Tier
) {
    fun toReferredCodeV2(): ReferredCodeV2 {
        return ReferredCodeV2(
                code = coupon.code,
                referredCouponTypeId = coupon.referredCouponTypeId,
                shareText = coupon.shareText,
                giftAndWin = coupon.giftAndWin,
                urlTermsAndConditions = coupon.urlTermsAndConditions,
                offers = coupon.offers,
                tier = tier
        )
    }
}

data class ReferredCodeV2(

        @SerializedName("referred_code")
        val code: String,

        @SerializedName("referred_coupon_type_id")
        val referredCouponTypeId: Int? = 0,

        @SerializedName("share_text")
        val shareText: String? = "",

        @SerializedName("gift_and_win")
        val giftAndWin: String? = "",

        @SerializedName("terms_and_conditions")
        val urlTermsAndConditions: String = "",

        @SerializedName("offers")
        val offers: List<Offers>? = null,

        @SerializedName("tier")
        val tier: Tier? = null
) {

    fun getMessageToShare(): String {
        val textToShare = shareText?.replace("RCODE", code).orEmpty()
        return "$textToShare \n" + getOfferList()
    }

    fun getOfferList(): String {
        var offersList = ""
        offers?.forEach { offersList += "${it.description}, " }
        return if (offersList.length > 2) {
            offersList.removeRange(offersList.length - 2, offersList.length)
        } else {
            offersList
        }.toUpperCase()
    }

    fun getAmountReceived(): Double {
        return offers?.firstOrNull { it.type == REFERRAL_EARN_TYPE }?.value?.toDouble()
                ?: 0.0
    }

}

data class Offers(
        @SerializedName("type")
        val type: String,

        @SerializedName("value")
        val value: String = "",

        @SerializedName("description")
        val description: String = ""
)

data class Tier(
        @SerializedName("tier")
        val tier: Int? = 0
)
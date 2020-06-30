package com.limapps.base.coupons.models

import com.limapps.common.tryOrDefault

enum class CouponConditionType(val value: String) {
    MIN_AMOUNT("minimum_amount"),
    PRODUCT_AVAILABILITY("product_availability"),
    PAYMENT_METHOD("payment_method"),
    STORE_TYPE("store_type_purchase"),
    IS_PRIME("is_prime"),
    CC_TYPE("cc_type"),
    BIN_CREDIT_CARD("bin_credit_card"),
    EXCLUDE_BIN_CREDIT_CARD("exclude_bin_credit_card");

    companion object Utils {

        private const val COUPON_CONDITION_PREFIX = "coupons."

        fun clearCouponErrorCode(rawErrorCode: String?): String? {
            return tryOrDefault({ rawErrorCode?.split(COUPON_CONDITION_PREFIX)?.get(1) }, null)
        }

        fun getCouponConditionFromError(rawErrorCode: String?): CouponConditionType? {
            return when (clearCouponErrorCode(rawErrorCode)) {
                MIN_AMOUNT.value -> MIN_AMOUNT
                PRODUCT_AVAILABILITY.value -> PRODUCT_AVAILABILITY
                PAYMENT_METHOD.value -> PAYMENT_METHOD
                STORE_TYPE.value -> STORE_TYPE
                IS_PRIME.value -> IS_PRIME
                CC_TYPE.value -> CC_TYPE
                BIN_CREDIT_CARD.value -> BIN_CREDIT_CARD
                EXCLUDE_BIN_CREDIT_CARD.value -> EXCLUDE_BIN_CREDIT_CARD
                else -> null
            }
        }
    }
}



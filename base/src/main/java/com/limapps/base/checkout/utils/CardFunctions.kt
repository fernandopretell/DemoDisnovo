package com.limapps.base.checkout.utils

import com.limapps.base.R

const val VISA = "visa"
const val VISA_SHORTCUT = "vi"
const val MASTER_CARD_INITIALS = "mc"
const val MASTER_CARD_SHORTCUT = "master"
const val MASTER_CARD = "mastercard"
const val AMERICAN_EXPRESS = "american_express"
const val AMERICAN_EXPRESS_SHORTCUT = "ax"
const val NO_CARD_ICON = -1
const val VISA_CHECKOUT_ORIGIN = "VisaCheckout"
const val MASTERPASS_ORIGIN = "Masterpass"
const val PAYPAL_ORIGIN = "PayPal"

fun getCreditCardIcon(type: String?): Int {
    return when (type) {
        VISA,
        VISA_SHORTCUT -> R.drawable.img_card_visa_wrapper
        MASTER_CARD,
        MASTER_CARD_SHORTCUT,
        MASTER_CARD_INITIALS -> R.drawable.img_card_master_wrapper
        AMERICAN_EXPRESS,
        AMERICAN_EXPRESS_SHORTCUT -> R.drawable.img_card_amex_wrapper
        else -> NO_CARD_ICON
    }
}


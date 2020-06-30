package com.limapps.base.checkout.utils

import com.limapps.base.R

const val CASH = "cash"
const val PAYPAL = "paypal"
const val DEBIT_CARD = "dc"
const val GOOGLE_PAY = "google_pay"
const val EDENRED = "edenred"
const val POS_TERMINAL = "pos_terminal"
const val CC = "cc"
const val MASTER_PASS = "masterpass"
const val PSE = "pse"

const val ADD_CARD = "add"
const val ADD_VISA_CHECKOUT = "visa_checkout"
const val RAPPI_PAY = "rappi_pay"

fun getPaymentName(key: String): Int = when (key) {
    CC -> R.string.new_eta_credit_card
    PSE -> R.string.base_pse
    PAYPAL -> R.string.base_paypal
    GOOGLE_PAY -> R.string.base_googlepay
    MASTER_PASS -> R.string.base_masterpass
    EDENRED ->  R.string.base_edenred
    DEBIT_CARD, POS_TERMINAL -> R.string.copy_pos_terminal
    else -> R.string.new_eta_cash
}

fun getPaymentIcon(key: String): Int = when (key) {
    CC, PAYPAL -> R.drawable.ic_credit_card_wrapper
    DEBIT_CARD, POS_TERMINAL -> R.drawable.ic_datafono_wrapper
    PSE -> R.drawable.ic_pse_wrapped
    MASTER_PASS -> R.drawable.ic_masterpass
    GOOGLE_PAY -> R.drawable.ic_gpay_mark
    else -> R.drawable.ic_cash_summary_wrapper
}
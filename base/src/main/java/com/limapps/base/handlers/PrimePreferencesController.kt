package com.limapps.base.handlers

interface PrimePreferencesController {

    fun isPrimePaymentMethodsOpen(): Boolean

    fun setPrimePaymentMethodsOpen(isOpen: Boolean)
}
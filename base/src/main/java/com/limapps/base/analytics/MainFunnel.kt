package com.limapps.base.analytics

interface MainFunnel {

    fun logSelectStoreType(map: Map<String, String>)

    fun logViewStoreType(map: Map<String, String>)

    fun logAddToCart(map: Map<String, String>, name: String, id: String, price: Double)

    fun logRemoveFromCart(map: Map<String, String>, name: String, id: String)

    fun logEmptyCart(map: Map<String, String>)

    fun logSelectEmptyCart(map: Map<String, String>)

    fun logCancelEmptyCart(map: Map<String, String>)

    fun logViewCart(map: Map<String, String>)

    fun logShowHardLimit(map: Map<String, String>)

    fun logProceedToCheckout(map: Map<String, String>)

    fun logOrderPlaced(map: Map<String, String>)

    fun logOrderPlacedConfirmed(map: Map<String, String>)

    fun logOrderPlacedConfirmedError(map: Map<String, String>)

    fun logHighValueEvent(map: Map<String, String>)
}
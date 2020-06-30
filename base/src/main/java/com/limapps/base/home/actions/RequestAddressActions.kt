package com.limapps.base.home.actions

sealed class RequestAddressActions {
    data class ShowDialog(val source: String) : RequestAddressActions()
    data class StartValidation(val source: String,
                               val onAddressNotNeeded: () -> Unit,
                               val onAddressNeeded: () -> Unit = {}) : RequestAddressActions()
    data class ValidateDistanceFromAddress(val source: String,
                                           val onContinueCallback: () -> Unit ) : RequestAddressActions()
}
package com.limapps.base.handlers

import io.reactivex.Observable

interface HomeTreatmentsController {
    fun shouldShowAutoComplete(): Boolean
    fun isLocationRequestTreatmentActive(): Boolean
    fun getLocationRequestTreatmentTitle(): String
    fun getAutocompleteType(): String
    fun getAutocompleteDelay(): Int
    fun getAutocompleteMinCharsLength(): Int
    fun shouldShowBillingSection(): Boolean?
    fun shouldShowNewAddressModal(): Boolean?
    fun shouldShowNewProfile(): Boolean?
    fun shouldShowNewMenu(): Boolean?
    //fun getHomeTreatments(): Observable<HashMap<String, Any>>
    fun wereHomeExperimentsLoaded(): Boolean
    fun getPhoneVerificationMethod(): String
}
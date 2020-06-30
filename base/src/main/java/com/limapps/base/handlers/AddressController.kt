package com.limapps.base.handlers

import com.limapps.base.location.Location
import com.limapps.base.models.Address
import com.limapps.base.models.SuggestedAddressResponse
import com.limapps.base.models.Zone
import com.limapps.base.utils.Optional
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface AddressController {

    fun fetchAndSaveAddress(): Single<List<Address>>

   /* @Deprecated("This method has been deprecated, use the reactive version", replaceWith = ReplaceWith("currentAddress()"))
    fun getCurrentAddress(): Address*/

    fun reloadCurrentAddress()

    fun resetAddress(): Completable

    fun hasAddressesSingle(): Single<Boolean>

    @Deprecated("This method has been deprecated, use the reactive version", replaceWith = ReplaceWith("hasAddressesSingle()"))
    fun hasAddresses(): Boolean

    fun getRawAddresses(): List<Address>

    fun getAddresses(): Observable<List<Address>>

    fun getAddressesWithoutErrorHandler(): Observable<List<Address>>

    fun currentAddress(): Observable<Address>

    fun outOfCoverage(): Observable<Address>

    fun delete(address: Address): Completable

    //fun updateAddress(address: Address): Completable

   // fun obtainSuggestedAddress(location: Location?): Single<SuggestedAddressResponse>

    fun selectCurrentLocation(): Completable

    fun getZone(): Single<Optional<Zone>>

    //fun getCurrentAddressOrCountryAddress(): Observable<Address>
}
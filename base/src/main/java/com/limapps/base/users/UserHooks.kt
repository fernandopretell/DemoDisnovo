package com.limapps.base.users

import com.limapps.base.models.Address
import com.limapps.base.users.models.RappiPayment
import com.limapps.base.users.models.RappiSubscription
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Providing and implementation of this interface is going to let the user controller knows about a
 * subscription and let it request the information when is need it
 */
interface SubscriptionHook {
    fun getSubscription(): Single<RappiSubscription>
}

/**
 * Providing and implementation of this interface is going to let the user controller knows about a
 * payment and let it request the information when is need it
 */
interface PaymentHook {
    fun getPayment(): Single<RappiPayment>
}

/**
 * Providing and implementation of this interface is going to let the user controller knows about
 * address updates on the app
 */
interface AddressHook {
    fun countryUpdate(): Observable<Address>
}
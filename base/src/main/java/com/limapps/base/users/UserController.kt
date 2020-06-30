package com.limapps.base.users

import com.limapps.base.models.UserInfoModel
import com.limapps.base.others.IResourceProvider
import com.limapps.base.users.models.RappiPayment
import com.limapps.base.users.models.RappiSubscription
import com.limapps.base.users.models.RappiUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


/**
 * This controller is the one in charge of storing the user information and keeps in memory some
 * additional information regarding payments and subscription to avoid the rest of the app to handle
 * and storing this data
 */
interface UserController {

    /**
     * This method should be called as soon as possible, when the profile could be obtained from the
     * server
     */
    fun initialize()

    /**
     * It's going to let you know if there are a valid user stored in the persistence
     */
    fun hasValidUser(): Single<Boolean>

    /**
     * Used to update the birthday for the user
     */
    fun updateUser(user: UserInfoModel): Completable

    /**
     * When the subscription information change you have to call this method to update the subscription
     * stored
     */
    fun updateSubscription(subscription: RappiSubscription)

    /**
     * When there is new information about a payment option you have to call this method to update
     * that information and let the rest of the app knows about those changes
     */
    fun updatePayment(payment: RappiPayment)

    /**
     * Used to update the identification number and type for the user
     */
    fun updateIdentification(identification: String, identificationType: Int): Completable

    /**
     * Used to update the birthday for the user
     */
    fun updateBirthday(birthday: String): Completable

    /**
     * Used to update the document information fof the user
     */
    fun updateDocumentInformation(birthday: String, documentNumber: String, documentType: Int): Completable

    /**
     * Return the User information in memory
     */
    fun getUser(): RappiUser

    /**
     * Return and observable where all the changes regarding to the user information is going to be
     * notified
     */
    fun getUserAsync(): Observable<RappiUser>

    /**
     * Return the subscription information in memory
     */
    fun getSubscription(): RappiSubscription

    /**
     * Return and observable where all the changes regarding to the Subscription information is going to be
     * notified
     */
    fun getSubscriptionAsync(): Observable<RappiSubscription>

    /**
     *Return the paymentMethodsNamesAvailableForPrime
     */
    fun getPaymentPrimeMethodNames(resourceProvider: IResourceProvider) : String

    /**
     * Return the payment information in memory
     */
    fun getPayments(): List<RappiPayment>

    /**
     * Return and observable where all the changes regarding to the user information is going to be
     * notified
     */
    fun getPaymentsAsync(): Observable<Map<String, RappiPayment>>

    /**
     * Return a observable with validation
     */
    fun isNewUser(): Boolean


    fun setNewUser(isNew: Boolean)

    /**
     * Destroy all the information stored in memory and in the persistence, must be called when the user
     * closes the session
     */
    fun cleanData()
}
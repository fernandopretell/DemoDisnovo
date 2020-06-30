package com.limapps.base.handlers

import com.limapps.base.credits.CreditsActions
import com.limapps.base.credits.RappiCredits
import com.limapps.base.models.rc.RappiCreditTransactionModel
import com.limapps.base.models.rc.RappiCreditsApplicationsResponse
import com.limapps.base.models.rc.RappiCreditsState
import com.limapps.base.utils.Optional
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface RappiCreditsController {

    fun isPreCheckoutFeatureEnabled(storeType: String): Boolean

    /**
     * Returns true if RC is enabled for payment
     */
    val creditsState: Observable<Optional<Boolean>>

    fun getRappiCreditsState(storeType: String): Single<RappiCreditsState>

    fun getRawValue(): Boolean

    /**
     * @return the list of available rappi credit transactions for the current order
     */
    fun getAvailableRappiCreditsTransactions(page: Int = 1, storeType: String): Single<RappiCreditsApplicationsResponse>

    /**
     * @return the list of  rappi credit transactions for the current order that do not apply
     */
    fun getUnavailableRappiCreditsTransactions(page: Int = 1, storeType: String): Single<RappiCreditsApplicationsResponse>

    /**
     * This method updates the current state for the user rappiCredits
     * @param isActive The new state to update
     * @return a Completable that represent the update action
     *
     */
    fun updateCreditState(isActive: Boolean): Completable


    /** Legacy interface for compatibility */
    val rappiCredits: PublishSubject<CreditsActions>

    fun getRappiCredits(): Single<RappiCredits>


    /**
     * return list of historical rappi credits
     */
    fun fetchHistory(type: String, page: Int, size: Int): Single<List<RappiCreditTransactionModel>>
}
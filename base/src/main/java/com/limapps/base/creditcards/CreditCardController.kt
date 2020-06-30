package com.limapps.base.creditcards

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface CreditCardController {

    fun getCreditCardsAndSave(origin: ScreenOrigin? = null): Single<List<ModelCreditCard>>
    fun deleteCreditCard(cardReference: String): Completable
    fun deletePayPal(cardReference: String): Completable
    fun setDefaultCard(cardReference: String): Completable
    fun setPaypalAsDefault(cardReference: String): Completable
    fun updateInstallments(installments: Int, accountId: String): Completable
    fun getInstallments(): Single<Int>
    fun hasKeyArg(cardReference: String): Boolean
    fun getKeyArg(cardReference: String): String
    fun setKeyArg(cardReference: String, cvv: String)
    fun loadCardsAndInstallments(storeId: String? = "",
                                 storeType: String? = null,
                                 hasAgeRestriction: Boolean = false,
                                 orderAmount: Double? = null,
                                 withValidation: Boolean? = true,
                                 origin: ScreenOrigin? = null): Single<MutableList<ModelCreditCard>>

    fun loadPayPalAccounts(): Single<List<ModelCreditCard>>
    fun requiresCvv(default: ModelCreditCard): Observable<Boolean>
    fun recacheCvv(creditCard: ModelCreditCard, cvv: String): Single<Boolean>
}

enum class ScreenOrigin(val value: String) {
    DEBT_SCREEN("DEBT"),
    RECHARGE_PAY("RECHARGE_PAY"),
    RAPPI_PRIME("PRIME"),
    VERIFICATION_SCREEN("VERIFICATION"),
    APP("APP"),
    RAPPI_PAY_PAYMENT("RAPPI_PAY_PAYMENT")
}
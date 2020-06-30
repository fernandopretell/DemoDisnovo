package com.limapps.base.creditcards

import com.limapps.base.creditcards.models.CardVerificationResponse
import com.limapps.base.creditcards.models.VerificationResponse
import com.limapps.base.support.model.NewSection
import io.reactivex.Single

interface CardsVerificationController {

    fun requestChargeForVerification(cardId: String): Single<VerificationResponse>

    fun validateChargedAmount(cardId: String, amount: Double): Single<CardVerificationResponse>

    fun getCardVerificationStatus(cardId: String): Single<VerificationResponse>

    fun getMessageVerification(name: String): String

    fun getSectionsAndArticlesByScreen(appScreenId: String, cardId: String): Single<List<NewSection>>

}
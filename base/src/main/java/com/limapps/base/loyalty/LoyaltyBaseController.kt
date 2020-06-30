package com.limapps.base.loyalty

import io.reactivex.Single

interface LoyaltyBaseController {

    fun hasLoyaltyModuleActive(): Single<Boolean>

    fun hasLoyaltyNotificationsActive(): Single<Boolean>

    fun hasLoyaltyTutorialActive(): Single<Boolean>

    fun hasLoyaltyOnBoardingActive(): Single<Boolean>

    fun hasLoyaltySideMenuActive(): Single<Boolean>

    fun hasBonusActive(): Single<Boolean>

    fun hasLoyaltyNewEtaActive(): Single<Boolean>

    fun hasOrderBenefitsActive(): Single<Boolean>

    fun hasLoyaltyRankingActive(): Single<Boolean>

    fun getInformationWebViewUrl(): String

    fun getPushNotificationTime(): Int

    fun getRankingLimit(): Int

}

package com.limapps.base.split.providers


interface CouponsFlowSplitProvider {
    fun isNewFlowEnabled() : Boolean

    fun listTabsTypes() : List<String>

    /**
     * returns what type of flow we are using (LIFO or WALLET)
     */
    fun getConfigurationType() : String

    fun isCouponTabsEnabled(): Boolean

    fun isBasketCouponProgressEnabled(): Boolean

    fun isNewRedemptionDialogEnabled(): Boolean

    fun isApplyForStoreMsgEnabled(): Boolean
}
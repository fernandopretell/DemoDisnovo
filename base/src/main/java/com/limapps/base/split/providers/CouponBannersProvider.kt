package com.limapps.base.split.providers


interface CouponBannersProvider {
    var couponType: CouponBannerType


    fun getTitle(): String
    fun getMessage(): String
    fun getBannerUrl(): String
    fun getItems(): List<String>?
    fun getColors(): List<String>
}

enum class CouponBannerType {
    PRIME,
    REFERRALS,
    GENERIC
}
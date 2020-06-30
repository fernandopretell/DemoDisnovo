package com.limapps.base.home

enum class BannerType {
    RESTAURANT, MARKET, OTHER;

    companion object {
        fun fromString(type: String): BannerType {
            return when (type.toUpperCase()) {
                "RESTAURANT" -> RESTAURANT
                "MARKET" -> MARKET
                else -> OTHER
            }
        }
    }
}

enum class OfferType {
    GLOBAL, COUPONS
}
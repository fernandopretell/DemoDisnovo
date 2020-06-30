package com.limapps.base.home

enum class HomeViewTypes(val value: String) {
    NONE(""),
    ACTIVE_ORDER("active_order"),
    RAPPI_PAY("rappi_pay"),
    MAIN("main"),
    MATRIX("matrix"),
    TITLE("title"),
    APP_LIST("app_list"),
    LAST_ORDERS("last_orders"),
    STORES_STORE_INFO("store_info"),
    STORES_STORE_CAROUSEL("store_carousel"),
    BRAZE("braze"),
    STORE_DESCRIPTION("store_description"),
    PRODUCT_CAROUSEL("product_carousel"),
    BANNERS("banners"),
    RECOMMENDATIONS("recommendations"),
    CREDIT_CARD_VERIFICATION("credit_card_verification"),
    GLOBAL_OFFERS("global_offers"),
    PRODUCTS("products"),
    LOADING("loading"),
    HEADER("header"),
    ORDER_IN_PROGRESS("order_in_progress"),
    ERROR_HOME("error_home"),
    PERSONALIZED_RECOMMENDATIONS("personalized_recommendations"),
    BORDERED_MATRIX("bordered_matrix"),
    MIXED("mixed"),
    MULTIPLE_PRODUCTS("multiple_products"),
    GLOBAL_SEARCH("global_search"),
    GAMIFICATION("gamification"),
    RECOMMENDED_CAROUSEL("carousel");

    companion object {

        fun valueOfType(type: String): HomeViewTypes {
            return values().first { it.value == type }
        }
    }
}
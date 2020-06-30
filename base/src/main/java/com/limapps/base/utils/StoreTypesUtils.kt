package com.limapps.base.utils

import com.limapps.base.R
import com.limapps.base.deepLink.GO_GRIN
import com.limapps.base.others.StoreTypeConstants.COURIER
import com.limapps.base.others.StoreTypeConstants.COURIER_HOURS
import com.limapps.base.others.StoreTypeConstants.EXPRESS
import com.limapps.base.others.StoreTypeConstants.HYPER_MARKET
import com.limapps.base.others.StoreTypeConstants.LIQUOR
import com.limapps.base.others.StoreTypeConstants.LIQUOR_SPECIAL
import com.limapps.base.others.StoreTypeConstants.LIQUOR_WINE
import com.limapps.base.others.StoreTypeConstants.MARKET
import com.limapps.base.others.StoreTypeConstants.PHARMACY
import com.limapps.base.others.StoreTypeConstants.RAPPI_CASH
import com.limapps.base.others.StoreTypeConstants.RENTBRELLA
import com.limapps.base.others.StoreTypeConstants.RESTAURANT
import com.limapps.base.others.StoreTypeConstants.SUPER_MARKET
import com.limapps.base.others.StoreTypeConstants.WHIM
import com.limapps.base.others.StoreTypeConstants.WIKI_WOMEN

const val HOME_TYPE_BY_CATEGORIES = "by_categories"
const val HOME_TYPE_BY_LINEARS = "by_linears"
const val HOME_TYPE_BY_STORES = "by_stores"
const val HOME_TYPE_BY_PRODUCTS = "by_products"
const val HOME_TYPE_DEEPLINK = "deeplink"
const val HOME_TYPE_WEB_VIEW = "web_view"

fun isWhimSectionActive(currentStoreType: String): Boolean {
    return currentStoreType == WHIM
}

fun isSplitOrderStore(homeType: String?): Boolean {
    return homeType == HOME_TYPE_BY_STORES
}

fun isBookingStoreType(storeType: String): Boolean {
    return storeType.contains("booking", true)
}

fun getImageOrderType(orderType: String?): Int {
    return if (!orderType.isNullOrBlank()) {
        when (orderType) {
            EXPRESS -> R.drawable.ic_express
            SUPER_MARKET, MARKET -> R.drawable.ic_super
            HYPER_MARKET -> R.drawable.ic_hyper
            RESTAURANT -> R.drawable.ic_comer
            PHARMACY -> R.drawable.ic_pharmacy
            LIQUOR, LIQUOR_SPECIAL, LIQUOR_WINE -> R.drawable.ic_liquors
            WIKI_WOMEN -> R.drawable.ic_wikimujeres
            RAPPI_CASH -> R.drawable.ic_rappicash
            WHIM -> R.drawable.ic_whim
            COURIER, COURIER_HOURS -> R.drawable.ic_package
            GO_GRIN -> R.drawable.ic_grin
            RENTBRELLA -> R.drawable.rentbrella_ic_logo
            else -> R.drawable.ic_express
        }
    } else {
        R.drawable.ic_whim
    }
}

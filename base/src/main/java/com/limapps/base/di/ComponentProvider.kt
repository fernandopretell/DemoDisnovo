package com.limapps.base.di

import dagger.android.HasAndroidInjector

interface ComponentProvider : HasAndroidInjector {
    fun clearComponent()
}

const val PAY_PROVIDER = "PAY_PROVIDER"
const val RESTAURANTS_PROVIDER = "RESTAURANTS_PROVIDER"
const val RAPPI_USA_PROVIDER = "RAPPI_USA_PROVIDER"
const val TAXI_PROVIDER = "TAXI_PROVIDER"
const val INVEST_PROVIDER = "INVEST_PROVIDER"
const val GLOBAL_BASKET_PROVIDER = "GLOBAL_BASKET_PROVIDER"
const val CHECKOUT_PROVIDER = "CHECKOUT_PROVIDER"
const val CHECKOUT_PROVIDERV2 = "CHECKOUT_PROVIDERV2"
const val FAVOR_PROVIDER = "FAVOR_PROVIDER"
const val ULTRA_SERVICE_PROVIDER = "ULTRA_SERVICE_PROVIDER"
const val WHIMS_PROVIDER = "WHIMS_PROVIDER"
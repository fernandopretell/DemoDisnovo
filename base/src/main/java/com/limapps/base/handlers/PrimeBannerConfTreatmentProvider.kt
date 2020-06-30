package com.limapps.base.handlers

interface PrimeBannerConfTreatmentProvider {

    fun getImageURL(): String

    fun getPrimeIcon(): String

    fun getTitle(): String

    fun getGradientColors(): List<String>

    fun getBannerConfMap(): Map<String, Any>
}
package com.limapps.base.analytics

import com.limapps.base.analytics.model.AnalyticsEvent
import com.limapps.base.analytics.model.AnalyticsEvent.ALLOW
import javax.inject.Inject

class LocationAllowedAnalytics @Inject constructor(
        //private val countryDataProvider: CountryDataProvider,
                                                   private val logger: AnalyticsAction) {
    operator fun invoke(granted: Boolean) {
        /*logger.log(
                AnalyticsEvent.ALLOW_LOCATION,
                hashMapOf(
                        Pair(AnalyticsEvent.LOCATION_ALLOWED, granted.toString())
                        //Pair(AnalyticsEvent.COUNTRY, countryDataProvider.getCountryName()
                        )
                )
        )*/
    }

    fun viewLocationScreen() {
        logger.log(AnalyticsEvent.VIEW_LOCATION_PERMISSION)
    }

    fun grantedLocationScreen() {
        logger.log(AnalyticsEvent.SELECT_LOCATION_PERMISSION_NATIVE, mapOf(ALLOW to true.toString()))
    }

    fun revokedLocationScreen() {
        logger.log(AnalyticsEvent.SELECT_LOCATION_PERMISSION_NATIVE, mapOf(ALLOW to false.toString()))
    }
}
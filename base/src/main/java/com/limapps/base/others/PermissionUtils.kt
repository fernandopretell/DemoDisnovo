package com.limapps.base.others

import com.limapps.base.analytics.LocationAllowedAnalytics

enum class PermissionConstants {
    GRANTED, REVOKED, ALREADY_ACCEPTED
}

fun PermissionConstants.isAllowed(): Boolean {
    return this == PermissionConstants.GRANTED || this == PermissionConstants.ALREADY_ACCEPTED
}

fun PermissionConstants.logAnalyticsIfIsNew(locationAllowedAnalytics: LocationAllowedAnalytics) {
    if(this == PermissionConstants.GRANTED){
        locationAllowedAnalytics.invoke(true)
    } else if(this == PermissionConstants.REVOKED){
        locationAllowedAnalytics.invoke(false)
    }
}
package com.limapps.base.analytics

interface AnalyticsConfig {

    fun updateUser(userId: String)

    fun getSessionId(): String

    fun getAnalyticsDeviceId(): String
}
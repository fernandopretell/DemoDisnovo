package com.limapps.base.analytics

interface AnalyticsAction {

    fun log(event: String, params: Map<String, String> = HashMap()): AnalyticsAction

    fun minLog(event: String, params: Map<String, String> = HashMap()): AnalyticsAction

}

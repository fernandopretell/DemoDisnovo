package com.limapps.base.home.analytics

interface HomeWidgetAnalyticsHelper {
    fun getSourceWidgetData(): Map<String, String>
    fun hasLoggedScrollDownThisAppExecution(): Boolean
}

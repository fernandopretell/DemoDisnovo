package com.limapps.base.analytics

import com.limapps.base.analytics.model.AnalyticsEvent
import javax.inject.Inject

class HomeExperimentsAnalytics @Inject constructor(private val logger: AnalyticsAction) {
    operator fun invoke() {
        logger.log(AnalyticsEvent.EXPERIMENTS_LOADED)
    }
}
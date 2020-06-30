package com.limapps.base.analytics.model

import android.os.Bundle

data class FacebookEvent(val event: String,
                         val valueToSum: Double = 0.0,
                         val parameters: Bundle = Bundle.EMPTY)

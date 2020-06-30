package com.limapps.base

import com.limapps.base.others.initOnce

object BaseConfig {

    var DEBUG: Boolean by initOnce()
    var APPLICATION_ID: String  by initOnce()
    var BUILD_TYPE: String  by initOnce()
    var FLAVOR: String  by initOnce()
    var VERSION_CODE: Int by initOnce()
    var VERSION_NAME: String  by initOnce()
    var AB_TESTING_DEBUG: Boolean by initOnce()
    var ALLOW_TEST_CC: Boolean by initOnce()
    var ANALYTICS_CUSTOM_CONFIG: Boolean by initOnce()
    var ANALYTICS_FILE_LOGGER: Boolean by initOnce()
    var AMPLITUDE_FLAG: Boolean by initOnce()
    var AMPLITUDE_DEV: Boolean by initOnce()
    var UXCAM_RELEASE: Boolean by initOnce()
    var ANALYTICS_FLAG: Boolean by initOnce()
    var APP_LOGS_FLAG: Boolean by initOnce()
    var DEBUG_WEB_VIEW: Boolean by initOnce()
    var IN_APP_FLAG: Boolean by initOnce()
    var SERVICE_LOGS_FLAG: Boolean by initOnce()
    var INTENT_PATH: String  by initOnce()
    var ENVIRONMENT: Environment by initOnce()
    var FMC_CACHE_EXPIRATION: Int by initOnce()
}


enum class Environment {
    PRODUCTION, DEVELOPMENT
}
package com.limapps.base.rest.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

internal const val REGULAR_TIMEOUT = 5
internal val REGULAR_TIMEOUT_UNIT = TimeUnit.SECONDS
internal const val LONG_TIMEOUT = 5
internal val LONG_TIMEOUT_UNIT = TimeUnit.MINUTES

/**
 * Interceptor responsible for managing connection timeouts depending on HTTP verbs.
 */
class ConnectionTimeOutInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Choose timeout based on HTTP verb
        val timeout = when (request.method()) {
            "GET" -> REGULAR_TIMEOUT to REGULAR_TIMEOUT_UNIT
            else -> LONG_TIMEOUT to LONG_TIMEOUT_UNIT
        }

        // Proceed with the request using above timeout settings
        return chain.withConnectTimeout(timeout.first, timeout.second)
                .withReadTimeout(timeout.first, timeout.second)
                .withWriteTimeout(timeout.first, timeout.second)
                .proceed(request)
    }
}

package com.limapps.base.rest.interceptors

import com.limapps.base.rest.CODE_KEY
import com.limapps.base.rest.MESSAGE_KEY
import com.limapps.base.rest.constants.HttpConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.Protocol
import okhttp3.MediaType
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException

internal const val MAX_RETRIES = 3
internal const val BASE_DELAY = 500

/**
 * Interceptor responsible for retrying a known set of response codes.
 */
class HttpRetryInterceptor : Interceptor {

    /**
     * Set of know response codes that is safe to make a retry.
     */
    private val targetCodes = setOf(408, 409, 429, 502, 503, 504, 598, 599)

    /**
     * Set of excluded verbs from retry operations
     */
    private val excludedVerbs = setOf("POST", "CONNECT")

    override fun intercept(chain: Interceptor.Chain): Response {
        // Try the request call
        val request = chain.request()
        var response = safeProceed(chain, request)

        // Exclude non-idempotent requests from retries
        if (excludedVerbs.contains(request.method())) {
            return response
        }

        // Retry until get a valid response or retry count has been exceed
        var retryCount = -1
        while (retryCount++ < MAX_RETRIES && targetCodes.contains(response.code())) {
            // Exponential wait time between retries
            val exponentialSleepTime = Math.round(Math.pow(2.0, retryCount.toDouble()) * BASE_DELAY)
            try {
                Thread.sleep(exponentialSleepTime)
                response = safeProceed(chain, request)
            } catch (e: InterruptedException) {
                // Restore interrupted status and exit loop
                Thread.currentThread().interrupt()
                break
            }
        }

        // Proceed with this response
        return response
    }

    @Throws(IOException::class)
    private fun safeProceed(chain: Interceptor.Chain, request: Request): Response {
        return try {
            chain.proceed(request)
        } catch (e: SocketTimeoutException) {
            Response.Builder().request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .message("Socket timeout")
                    .body(ResponseBody.create(MediaType.parse(HttpConstants.APPLICATION_JSON), JSONObject().apply {
                        put(MESSAGE_KEY, "Socket timeout")
                        put(CODE_KEY, "408")
                    }.toString()))
                    .code(408)
                    .build()
        }
    }
}

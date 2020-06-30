package com.limapps.base.rest.interceptors

import android.os.Bundle
import com.limapps.base.interfaces.RappiBroadcast
import com.limapps.base.utils.LogUtil
import com.limapps.common.tryOrDefault
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.nio.charset.Charset

const val CODE = "x-two-factor-code"
const val AUTH_KEY = "x-two-factor-auth"
const val EMAIL_OPTION = "email_option"
const val FLOW_TYPE = "flow_type"
const val FLOW_RECOVER_ACCOUNT = "recover_account"
const val FLOW_LOGIN = "login"

const val AUTH_MESSAGE_TYPE = "auth_type"
const val AUTH_MESSAGE_VALUE = "auth_value"
const val AUTH_URL = "auth_url"

const val AUTHENTICATION_NEEDED = "authentication_needed"
const val AUTHENTICATION_RESOLVED = "authentication_resolved"
const val AUTHENTICATION_INVALID_CODE = "authentication_invalid_code"
const val AUTHENTICATION_VALIDATED = "authentication_validated"
const val AUTHENTICATION_DISMISS = "authentication_dismiss"

class AccountVerificationInterceptor(private val rappiBroadcast: RappiBroadcast) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)

        return handleResponseFlow(chain, response)

    }

    private fun handleResponseFlow(chain: Interceptor.Chain, response: Response, afterValidation: Boolean = false): Response {
        if (response.code() == 400) {

            return tryOrDefault({
                val jsonObject = JSONObject(getBodyFromResponse(response))
                val errorBody = jsonObject.getJSONObject("error")
                val errorCode = errorBody.getString("code")

                return when (errorCode) {
                    "verification_code_required" -> handleAccountValidationError(chain, response, errorBody)
                    "invalid_code" -> handleInvalidCodeResponse(chain, response)
                    else -> {
                        handleDifferentError()
                        response
                    }
                }
            }, response)
        } else {
            if (afterValidation) {
                rappiBroadcast.sendBroadcastWithAction(AUTHENTICATION_VALIDATED, Bundle(0))
            }
            return response
        }
    }

    private fun handleDifferentError() {
        rappiBroadcast.sendBroadcastWithAction(AUTHENTICATION_DISMISS, Bundle(0))
    }

    private fun handleInvalidCodeResponse(chain: Interceptor.Chain, response: Response): Response {
        val newResponse = handleErrorForCodeResponse(chain, response)
        return if (newResponse.code() == 200) {
            newResponse
        } else {
            handleResponseFlow(chain, newResponse, true)
        }
    }

    private fun handleAccountValidationError(chain: Interceptor.Chain,
                                             response: Response,
                                             errorBody: JSONObject): Response {

        val bundle = Bundle(6)

        bundle.putString(AUTH_MESSAGE_TYPE, errorBody.getString("verification_type"))
        bundle.putString(AUTH_MESSAGE_VALUE, errorBody.getString("verification_value"))
        bundle.putString(AUTH_URL, response.request()?.url().toString())
        bundle.putString(AUTH_KEY, errorBody.getString("key"))

        bundle.putString(EMAIL_OPTION,  tryOrDefault({
            errorBody.getString("retry_by_email")
        }, EMAIL_OPTION))

        bundle.putString(FLOW_TYPE,  tryOrDefault({
            errorBody.getString("flow_type")
        }, FLOW_TYPE))

        rappiBroadcast.sendBroadcastWithAction(AUTHENTICATION_NEEDED, bundle)

        return tryOrDefault({
            val newResponse = waitDataAndGenerateNewResponse(chain, response)
            handleResponseFlow(chain, newResponse, true)
        }, response)

    }

    private fun handleErrorForCodeResponse(chain: Interceptor.Chain, response: Response): Response {

        rappiBroadcast.sendBroadcastWithAction(AUTHENTICATION_INVALID_CODE, Bundle(0))

        return tryOrDefault({
            waitDataAndGenerateNewResponse(chain, response)
        }, response)

    }

    private fun waitDataAndGenerateNewResponse(chain: Interceptor.Chain, response: Response): Response {
        val responseFromBroadcast = rappiBroadcast.registerBroadcastForAction(AUTHENTICATION_RESOLVED)
                .blockingFirst()

        val mapResponse = responseFromBroadcast.toNullable() ?: emptyMap()

        val newRequest = response.request()
                ?.newBuilder()
                ?.header(CODE, mapResponse[CODE].orEmpty())
                ?.header(AUTH_KEY, mapResponse[AUTH_KEY].orEmpty())
                ?.build()

        return chain.proceed(newRequest!!)
    }

    private fun getBodyFromResponse(response: Response): String {
        val responseBody = response.body()
        val source = responseBody?.source()
        source?.request(Long.MAX_VALUE)
        val buffer = source?.buffer()

        val responseBodyString = buffer?.clone()?.readString(Charset.forName("UTF-8"))
        LogUtil.d("Interceptor", responseBodyString)
        return responseBodyString.orEmpty()
    }
}
package com.limapps.base.rest

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.limapps.base.analytics.model.AnalyticsEvent.ERROR_BODY
import com.limapps.base.analytics.model.AnalyticsEvent.ERROR_CODE
import com.limapps.base.analytics.model.AnalyticsEvent.ERROR_MESSAGE
import com.limapps.base.analytics.model.AnalyticsEvent.ERROR_TYPE
import com.limapps.base.analytics.model.AnalyticsEvent.SERVER_URL
import com.limapps.base.models.ServerErrorResponse
import com.limapps.base.utils.StringUtils
import com.limapps.common.getJsonObjectOrDefault
import com.limapps.common.tryOrDefault
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

const val ERROR_KEY = "error"
const val ERRORS_KEY = "errors"
const val URL_KEY = "url"
const val MESSAGE_KEY = "message"
const val DETAIL_KEY = "detail"
const val SEVERITY_KEY = "severity"
const val CODE_KEY = "code"
const val ORDERS_KEY = "orders"

data class ServerException internal constructor(override val message: String = "",
                                                val response: Response<*>?,
                                                val kind: Kind,
                                                val exception: Throwable?,
                                                val serverErrorResponse: ServerErrorResponse? = null,
                                                val url: String = "",
                                                val code: String = "",
                                                val severity: String? = null,
                                                val rawError: String? = null) : RuntimeException(message, exception)

fun ServerException.isNetworkException(): Boolean {
    return kind == Kind.NETWORK
}

/** Identifies the event kind which triggered a [ServerException].  */
enum class Kind {
    /** An [IOException] occurred while communicating to the server.  */
    NETWORK,
    /** A non-200 HTTP status code was received from the server.  */
    HTTP,
    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
}

fun httpError(url: String, response: Response<*>, genericError: String): ServerException {
    val serverErrorBody = getErrorMessage(response)
    val serverCode = response.code().toString()
    val serverErrorResponse = getReadableError(serverErrorBody, genericError, serverCode)
    val errorBody = getErrorBody(serverErrorBody)?.toString() ?: ""
    val serverErrorMessage = serverErrorResponse.message
    return ServerException(serverErrorResponse.message, response, Kind.HTTP, null,
        serverErrorResponse, url, serverErrorResponse.code, serverErrorResponse.severity,
        serverErrorBody.toString())
}

private fun getErrorBody(serverErrorBody: JsonObject): JsonObject? {
    return serverErrorBody.getJsonObjectOrDefault(ERRORS_KEY)
            ?: serverErrorBody.getJsonObjectOrDefault(ERROR_KEY)
}

private fun getErrorMessage(response: Response<*>): JsonObject {
    return tryOrDefault({
        Gson().fromJson(response.errorBody()?.string(), JsonObject::class.java)
    }, JsonObject())
}

fun networkError(exception: IOException, networkError: String): ServerException {
    return ServerException(networkError, null, Kind.NETWORK, exception)
}

fun unexpectedError(exception: Throwable, url: String? = null): ServerException {

    return ServerException(exception.message.orEmpty(), null, Kind.UNEXPECTED, exception)
}

fun getReadableError(jsonResponse: JsonObject, genericError: String, serverCode: String): ServerErrorResponse {

    var message = genericError
    var severity = ""
    var code = ""
    var ordersWithDebt = ""
    var url = ""

    try {
        val jsonObject = JSONObject(jsonResponse.toString())
        var jsonError: JSONObject? = null
        when {
            hasJsonFormat(jsonObject, ERROR_KEY) -> jsonError = jsonObject.getJSONObject(ERROR_KEY)
            hasJsonFormat(jsonObject, ERRORS_KEY) -> jsonError = jsonObject.getJSONObject(ERRORS_KEY)
            jsonObject.has(ERRORS_KEY) -> jsonError = jsonObject.getJSONObject(ERRORS_KEY)
            jsonObject.has(URL_KEY) -> jsonError = jsonObject
            jsonObject.has(MESSAGE_KEY) -> jsonError = jsonObject
        }

        jsonError?.let {
            if (it.has(MESSAGE_KEY)) {
                message = jsonError.getString(MESSAGE_KEY)
            }

            if (it.has(DETAIL_KEY)) {
                message += ". " + it.getJSONObject(DETAIL_KEY).getString(MESSAGE_KEY)
            }

            if (it.has(SEVERITY_KEY)) {
                severity = jsonError.getString(SEVERITY_KEY)
            }

            if (it.has(CODE_KEY)) {
                code = it.getString(CODE_KEY)
            }

            if (it.has(ORDERS_KEY)) {
                ordersWithDebt = it.getString(ORDERS_KEY)
            }
            if (it.has(URL_KEY)) {
                url = it.getString(URL_KEY)
            }
        }
        return ServerErrorResponse(message, code, severity, ordersWithDebt, url, serverCode = serverCode)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return ServerErrorResponse(genericError)
}

fun hasJsonFormat(json: JSONObject, key: String): Boolean {
    return json.has(key) && StringUtils.isJSONValid(json.getString(key))
}

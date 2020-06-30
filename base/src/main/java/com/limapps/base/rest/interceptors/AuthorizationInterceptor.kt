package com.limapps.base.rest.interceptors

import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.rest.constants.HttpConstants
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val userSettings = PreferencesManager

        val accessToken = userSettings.accessToken().get()

        val requestBuilder = chain.request().newBuilder()

        if (accessToken.isNotEmpty()) {
            requestBuilder.header(HttpConstants.AUTHORIZATION, HttpConstants.BEARER + " " + accessToken)
        }

        val finalRequest = requestBuilder.build()

        return chain.proceed(finalRequest)
    }
}
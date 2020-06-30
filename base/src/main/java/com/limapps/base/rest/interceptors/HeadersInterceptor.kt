package com.limapps.base.rest.interceptors

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.limapps.base.others.RappiData
import com.limapps.base.rest.constants.HttpConstants
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()

        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
        requestBuilder.header(HttpConstants.DEVICE_ID, RappiData.androidID)
                .header(HttpConstants.ACCEPT, HttpConstants.APPLICATION_JSON)
                .header(HttpConstants.USER_AGENT, System.getProperty("http.agent"))
                .header(HttpConstants.CONTENT_TYPE, HttpConstants.APPLICATION_JSON)
                .header(HttpConstants.APP_VERSION, RappiData.appVersion)
                .header(HttpConstants.APP_VERSION_NAME, RappiData.appVersionName)
                .header(HttpConstants.LANGUAGE, locale.language.toLowerCase())
                .header(HttpConstants.ACCEPT_LANGUAGE, locale.toLanguageTag())
                .header(HttpConstants.TIMESTAMP, System.currentTimeMillis().toString())
                .header(HttpConstants.ADVERTISER_ID, RappiData.advertiserID.orEmpty())
                //.header(HttpConstants.APPSFLYER_ID, AppsFlyerHandler.getAppsFlyerUID())
                .method(chain.request().method(), chain.request().body())

        val finalRequest = requestBuilder.build()

        return chain.proceed(finalRequest)
    }
}
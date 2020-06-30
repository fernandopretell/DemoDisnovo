package com.limapps.base.rest

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import android.text.TextUtils
import com.limapps.base.BaseConfig
import com.limapps.base.others.RappiData
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.rest.constants.HttpConstants
import com.limapps.base.utils.LogUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Create and build only one instance of OkHttpClient
 */
object OkHttpClientGenerator {

    private val httpClientBuilder = OkHttpClient.Builder()
    private var client: OkHttpClient? = null

    @Synchronized
    fun getClient(): OkHttpClient {
        return client ?: synchronized(this) {
            client ?: createClient().also { client = it }
        }
    }

    private fun createClient(): OkHttpClient {
        addRetrofitInterceptor()
        initializeLoggingInterceptor()
        setTimeouts()
        //TODO this should be enabled
        //            httpClientBuilder.authenticator(new TokenAuthenticator(RappiApplication.getInstance(),
        //                    new AuthRepository(ServiceGenerator.createService(AuthService.class))));
        return httpClientBuilder.build()
    }

    @Synchronized
    fun invalidateClient() {
        client = null
    }

    private fun setTimeouts() {
        httpClientBuilder.readTimeout(5, TimeUnit.MINUTES)
        httpClientBuilder.connectTimeout(5, TimeUnit.MINUTES)
    }

    private fun initializeLoggingInterceptor() {
        if (BaseConfig.SERVICE_LOGS_FLAG) {
            val logging = HttpLoggingInterceptor { message -> LogUtil.e("SERVER", message) }
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(logging)
        }
    }

    private fun addRetrofitInterceptor() {
        httpClientBuilder.addInterceptor { chain ->

            val userSettings = PreferencesManager
            val accessToken = userSettings.accessToken().get()

            val requestBuilder = chain.request().newBuilder()
            if (!TextUtils.isEmpty(accessToken)) {
                requestBuilder.header(HttpConstants.AUTHORIZATION, HttpConstants.BEARER + " " + accessToken)
            }

            val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
            requestBuilder
                    .header(HttpConstants.DEVICE_ID, RappiData.androidID)
                    .header(HttpConstants.ACCEPT, HttpConstants.APPLICATION_JSON)
                    .header(HttpConstants.USER_AGENT, System.getProperty("http.agent"))
                    .header(HttpConstants.CONTENT_TYPE, HttpConstants.APPLICATION_JSON)
                    .header(HttpConstants.APP_VERSION, RappiData.appVersion)
                    .header(HttpConstants.LANGUAGE, locale.language.toLowerCase())
                    .header(HttpConstants.ACCEPT_LANGUAGE, locale.toLanguageTag())
                    .method(chain.request().method(), chain.request().body())

            val finalRequest = requestBuilder.build()

            chain.proceed(finalRequest)
        }
    }

}

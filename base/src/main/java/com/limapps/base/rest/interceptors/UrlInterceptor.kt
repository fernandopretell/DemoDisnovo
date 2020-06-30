package com.limapps.base.rest.interceptors

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class UrlInterceptor : Interceptor {

    private var host: String? = null

    fun setHost(host: String) {
        this.host = HttpUrl.parse(host)?.url()?.toURI()?.host
    }

    fun resetHost() {
        this.host = null
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        host?.let {

            val newUrl = request.url().newBuilder()
                    .scheme("https")
                    .host(it)
                    .build()

            request = request.newBuilder()
                    .url(newUrl)
                    .build()
        }

        return chain.proceed(request)
    }
}
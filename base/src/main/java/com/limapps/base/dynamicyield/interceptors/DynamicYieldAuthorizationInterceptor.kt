package com.limapps.base.dynamicyield.interceptors

import com.limapps.base.R
import com.limapps.base.others.IResourceProvider
import com.limapps.base.rest.constants.HttpConstants
import okhttp3.Interceptor
import okhttp3.Response

class DynamicYieldAuthorizationInterceptor(
        val resourcesProvider: IResourceProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()

        requestBuilder.header(HttpConstants.DY, resourcesProvider.getString(R.string.dynamic_yield_api_key))
        requestBuilder.header(HttpConstants.CONTENT_TYPE, HttpConstants.APPLICATION_JSON)

        val finalRequest = requestBuilder.build()

        return chain.proceed(finalRequest)
    }
}
package com.limapps.base.rest

import com.limapps.base.persistence.preferences.PreferencesManager

/**
 * Class responsible for creating retrofit http services
 */
//TODO change to dagger
@Deprecated("")
object ServiceGenerator {

    fun <S> createService(serviceClass: Class<S>): S {
        val basePath = PreferencesManager.basePath().get()
        val builder = RetrofitBuilderGenerator.getRetrofitBuilder(basePath)

        val client = OkHttpClientGenerator.getClient()
        val retrofit = builder.client(client).build()

        return retrofit.create(serviceClass)
    }

}

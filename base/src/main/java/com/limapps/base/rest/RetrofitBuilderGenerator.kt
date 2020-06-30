package com.limapps.base.rest

import com.limapps.base.R
import com.limapps.base.others.ResourcesProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Create only one instance of Retrofit.Builder
 */
@Deprecated("Use the clients provided on the dagger graph")
object RetrofitBuilderGenerator {

    private var retrofitBuilder: Retrofit.Builder? = null

    @Synchronized
    fun getRetrofitBuilder(baseUrl: String): Retrofit.Builder {
        return retrofitBuilder ?: synchronized(this) {
            retrofitBuilder ?: createRetrofitBuilder(baseUrl).also { retrofitBuilder = it }
        }
    }

    fun invalidateBuilder() {
        retrofitBuilder = null
    }

    private fun createRetrofitBuilder(baseUrl: String): Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactoryKt.create(
                        ResourcesProvider.getString(R.string.error_server),
                        ResourcesProvider.getString(R.string.error_network)))
    }
}

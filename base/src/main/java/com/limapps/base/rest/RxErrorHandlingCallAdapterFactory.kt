package com.limapps.base.rest

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactoryKt(private val genericError: String,
                                          private val networkError: String) : CallAdapter.Factory() {

    private val original: RxJava2CallAdapterFactory by lazy { RxJava2CallAdapterFactory.create() }

    override fun get(returnType: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): CallAdapter<*, *>? {
        val temporal = original.get(returnType, annotations, retrofit) as? CallAdapter<in Any, out Any>
        return if (temporal != null) {
            RxCallAdapterWrapper(genericError, networkError, temporal)
        } else null
    }

    companion object {
        fun create(genericError: String, networkError: String): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactoryKt(genericError, networkError)
        }
    }
}

private class RxCallAdapterWrapper(private val genericError: String,
                                   private val networkError: String,
                                   private val wrapped: CallAdapter<in Any, out Any>) : CallAdapter<Any, Any> {

    override fun responseType(): Type {
        return wrapped.responseType()
    }

    override fun adapt(call: Call<Any>?): Any {
        val adaptedCall = wrapped.adapt(call)

        val url = call?.request()?.url().toString()

        return when (adaptedCall) {
            is Completable -> adaptedCall.onErrorResumeNext { throwable -> Completable.error(asRetrofitException(throwable, url)) }
            is Single<*> -> adaptedCall.onErrorResumeNext { throwable -> Single.error(asRetrofitException(throwable, url)) }
            is Observable<*> -> adaptedCall.onErrorResumeNext { throwable: Throwable -> Observable.error(asRetrofitException(throwable, url)) }
            is Flowable<*> -> adaptedCall.onErrorResumeNext { throwable: Throwable -> Flowable.error(asRetrofitException(throwable, url)) }
            else -> throw RuntimeException("Observable Type not supported")
        }
    }

    private fun asRetrofitException(throwable: Throwable, url: String?): ServerException {
        return when (throwable) {
            is HttpException -> {
                val response = throwable.response()
                return httpError(response?.raw()?.request()?.url().toString(), response!!, genericError)
            }
            // IOException -> networkError(throwable, networkError)
            else -> unexpectedError(throwable, url)
        }
    }
}
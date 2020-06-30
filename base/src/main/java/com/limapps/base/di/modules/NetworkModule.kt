package com.limapps.base.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.limapps.base.BaseConfig
import com.limapps.base.R
import com.limapps.base.di.modules.JsonAndXmlConverters.QualifiedTypeConverterFactory
import com.limapps.base.di.qualifiers.AppLoggingInterceptors
import com.limapps.base.di.qualifiers.AuthorizationInterceptors
import com.limapps.base.di.qualifiers.BasePath
import com.limapps.base.di.qualifiers.BasicServices
import com.limapps.base.di.qualifiers.CentralizedService
import com.limapps.base.di.qualifiers.DynamicYield
import com.limapps.base.di.qualifiers.GeneralInterceptors
import com.limapps.base.di.qualifiers.MicroService
import com.limapps.base.di.qualifiers.Spreedly
import com.limapps.base.di.qualifiers.SwitchServices
import com.limapps.base.dynamicyield.interceptors.DynamicYieldAuthorizationInterceptor
import com.limapps.base.interfaces.RappiBroadcast
import com.limapps.base.network.Tls12SocketFactory
import com.limapps.base.others.IResourceProvider
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.rest.RxErrorHandlingCallAdapterFactoryKt
import com.limapps.base.rest.interceptors.AuthorizationInterceptor
import com.limapps.base.rest.interceptors.ConnectionTimeOutInterceptor
import com.limapps.base.rest.interceptors.HeadersInterceptor
import com.limapps.base.rest.interceptors.UrlInterceptor
import com.limapps.base.rest.interceptors.HttpRetryInterceptor
import com.limapps.base.rest.interceptors.AccountVerificationInterceptor
import com.limapps.base.rest.interceptors.BasePathUrlInterceptor
import com.limapps.base.rest.interceptors.MicroServicesUrlInterceptor
import com.limapps.base.rest.interceptors.CentralizedUrlInterceptor
import com.limapps.base.utils.LogUtil
import com.limapps.common.isLollipopOrMinor
import com.limapps.common.tryOrPrintException
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Authenticator
import okhttp3.CertificatePinner
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.security.KeyStore
import java.util.Arrays
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import kotlin.collections.ArrayList
import kotlin.collections.Set
import kotlin.collections.arrayListOf
import kotlin.collections.forEach
import kotlin.collections.mutableListOf

const val RAPPI_AUTHENTICATOR = "Authenticator"

@Module
object NetworkModule {
    @Provides
    @Singleton
    @JvmStatic
    fun gson(/*typeAdapters: @JvmSuppressWildcards Set<TypeAdapterFactory>*/): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun urlInterceptor(): UrlInterceptor = UrlInterceptor()

    @Provides
    @Singleton
    @AuthorizationInterceptors
    @JvmStatic
    fun authorizationInterceptors(@GeneralInterceptors generalInterceptors: ArrayList<Interceptor>): ArrayList<Interceptor> {
        val authorizationArray: ArrayList<Interceptor> = ArrayList()
        authorizationArray.add(AuthorizationInterceptor())
        generalInterceptors.forEach { authorizationArray.add(it) }
        return authorizationArray
    }

    @Provides
    @Singleton
    @GeneralInterceptors
    @JvmStatic
    fun generalInterceptors(rappiBroadcast: RappiBroadcast): ArrayList<Interceptor> {
        return arrayListOf(
                ConnectionTimeOutInterceptor(),
                HeadersInterceptor(),
                getLoggingInterceptor(),
                HttpRetryInterceptor(),
                AccountVerificationInterceptor(rappiBroadcast)
        )
    }

    @Provides
    @JvmStatic
    fun okhttpTimeout(): OkHttpClient.Builder {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.readTimeout(5, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS)
        //if (!BaseConfig.APP_LOGS_FLAG) {
        //    httpClientBuilder.certificatePinner(certificatePinner)
       // }
        return httpClientBuilder
    }

    /**
     * retrofit clients
     * */

    @Provides
    @Singleton
    @BasicServices
    @JvmStatic
    fun basicServicesClient(
            @BasicServices httpClient: OkHttpClient,
            resourceProvider: IResourceProvider,
            gson: Gson
    ): Retrofit {
        return retrofitBuilder(httpClient, PreferencesManager.baseCentralizedService().get(),
                resourceProvider.getString(R.string.error_server),
                resourceProvider.getString(R.string.error_network),
                gson).build()
    }

    @Provides
    @Singleton
    @BasePath
    @JvmStatic
    fun basePathClient(
            @BasePath httpClient: OkHttpClient,
            resourceProvider: IResourceProvider,
            gson: Gson
    ): Retrofit {
        return retrofitBuilder(httpClient, PreferencesManager.basePath().get(),
                resourceProvider.getString(R.string.error_server),
                resourceProvider.getString(R.string.error_network),
                gson).build()
    }

    @Provides
    @Singleton
    @SwitchServices
    @JvmStatic
    fun generalClient(
            @SwitchServices httpClient: OkHttpClient,
            resourceProvider: IResourceProvider,
            gson: Gson
    ): Retrofit {
        return retrofitBuilder(httpClient, PreferencesManager.basePath().get(),
                resourceProvider.getString(R.string.error_server),
                resourceProvider.getString(R.string.error_network),
                gson).build()
    }

    @Provides
    @Singleton
    @MicroService
    @JvmStatic
    fun microServiceClient(
            @MicroService httpClient: OkHttpClient,
            resourceProvider: IResourceProvider,
            gson: Gson
    ): Retrofit {
        return retrofitBuilder(httpClient, PreferencesManager.baseMicroService().get(),
                resourceProvider.getString(R.string.error_server),
                resourceProvider.getString(R.string.error_network),
                gson).build()
    }

    @Provides
    @Singleton
    @CentralizedService
    @JvmStatic
    fun centralizedClient(
            @CentralizedService httpClient: OkHttpClient,
            resourceProvider: IResourceProvider,
            gson: Gson
    ): Retrofit {
        return retrofitBuilder(httpClient, PreferencesManager.baseCentralizedService().get(),
                resourceProvider.getString(R.string.error_server),
                resourceProvider.getString(R.string.error_network),
                gson).build()
    }

    @Provides
    @Singleton
    @Spreedly
    @JvmStatic
    fun spreedlyClient(
            @Spreedly httpClient: OkHttpClient,
            resourceProvider: IResourceProvider,
            gson: Gson
    ): Retrofit {
        return retrofitBuilder(httpClient, resourceProvider.getString(R.string.spreedly_base_url),
                resourceProvider.getString(R.string.error_server),
                resourceProvider.getString(R.string.error_network),
                gson).build()
    }

    @Provides
    @Singleton
    @DynamicYield
    @JvmStatic
    fun dynamicClient(
            @DynamicYield httpClient: OkHttpClient,
            resourceProvider: IResourceProvider,
            gson: Gson
    ): Retrofit {
        return retrofitBuilder(httpClient, resourceProvider.getString(R.string.dynamic_yield_base_url),
                resourceProvider.getString(R.string.error_server),
                resourceProvider.getString(R.string.error_network),
                gson).build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun getSimpleRetrofitBuilder(resourceProvider: IResourceProvider,
                                 gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(QualifiedTypeConverterFactory(
                        GsonConverterFactory.create(gson),
                        SimpleXmlConverterFactory.createNonStrict(Persister(AnnotationStrategy()))))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactoryKt.create(resourceProvider.getString(R.string.error_server),
                        resourceProvider.getString(R.string.error_network)))
    }

    /**
     * okhttp clients
     * */

    @Provides
    @Singleton
    @BasicServices
    @JvmStatic
    fun basicOkhttp(
            httpClientBuilder: OkHttpClient.Builder,
            @AuthorizationInterceptors interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        interceptors.forEach { httpClientBuilder.addInterceptor(it) }
        httpClientBuilder.addInterceptor(BasePathUrlInterceptor())
        addStethoNetworkInterceptor(httpClientBuilder)
        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    @SwitchServices
    @JvmStatic
    fun generalOkhttp(
            httpClientBuilder: OkHttpClient.Builder,
            @GeneralInterceptors interceptors: ArrayList<Interceptor>,
            urlInterceptor: UrlInterceptor
    ): OkHttpClient {
        httpClientBuilder.addInterceptor(urlInterceptor)
        interceptors.forEach { httpClientBuilder.addInterceptor(it) }
        addStethoNetworkInterceptor(httpClientBuilder)
        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    @BasePath
    @JvmStatic
    fun basePathOkhttp(
            httpClientBuilder: OkHttpClient.Builder,
            //@Named(RAPPI_AUTHENTICATOR) tokenAuthenticator: Authenticator,
            @AuthorizationInterceptors interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        //httpClientBuilder.authenticator(tokenAuthenticator)
        httpClientBuilder.addInterceptor(BasePathUrlInterceptor())
        interceptors.forEach { httpClientBuilder.addInterceptor(it) }
        addStethoNetworkInterceptor(httpClientBuilder)
        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    @MicroService
    @JvmStatic
    fun microServiceOkhttp(
            httpClientBuilder: OkHttpClient.Builder,
            //@Named(RAPPI_AUTHENTICATOR) tokenAuthenticator: Authenticator,
            @AuthorizationInterceptors interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        //httpClientBuilder.authenticator(tokenAuthenticator)
        httpClientBuilder.addInterceptor(MicroServicesUrlInterceptor())
        interceptors.forEach { httpClientBuilder.addInterceptor(it) }
        addStethoNetworkInterceptor(httpClientBuilder)
        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    @CentralizedService
    @JvmStatic
    fun centralizedOkhttp(
            httpClientBuilder: OkHttpClient.Builder,
           // @Named(RAPPI_AUTHENTICATOR) tokenAuthenticator: Authenticator,
            @AuthorizationInterceptors interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
       // httpClientBuilder.authenticator(tokenAuthenticator)
        httpClientBuilder.addInterceptor(CentralizedUrlInterceptor())
        interceptors.forEach { httpClientBuilder.addInterceptor(it) }
        addStethoNetworkInterceptor(httpClientBuilder)
        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    @Spreedly
    @JvmStatic
    fun spreedlyOkhttp(httpClientBuilder: OkHttpClient.Builder,
                       resourceProvider: IResourceProvider): OkHttpClient {
        httpClientBuilder.addInterceptor(getLoggingInterceptor())
        httpClientBuilder.addInterceptor { chain ->
            val request = chain.request()
            val environmentKey = resourceProvider.getString(R.string.spreedly_env_key)
            val url = request.url().newBuilder()
                    .addQueryParameter("environment_key", environmentKey)
                    .build()
            val newRequest = request.newBuilder().url(url).build()
            chain.proceed(newRequest)
        }
        addStethoNetworkInterceptor(httpClientBuilder)
        return enabledTLS(httpClientBuilder).build()
    }

    private fun addStethoNetworkInterceptor(httpClientBuilder: OkHttpClient.Builder) {
        if (BaseConfig.APP_LOGS_FLAG) {
            httpClientBuilder.addNetworkInterceptor(StethoInterceptor())
        }
    }

    @Provides
    @Singleton
    @DynamicYield
    @JvmStatic
    fun dynamicYieldOkhttp(
            httpClientBuilder: OkHttpClient.Builder,
            resourceProvider: IResourceProvider
    ): OkHttpClient {
        httpClientBuilder.addInterceptor(DynamicYieldAuthorizationInterceptor(resourceProvider))
        addStethoNetworkInterceptor(httpClientBuilder)
        return httpClientBuilder.build()
    }

    @Provides
    @Reusable
    @JvmStatic
    @AppLoggingInterceptors
    fun provideAppLoggingInterceptor(): Interceptor {
        return getLoggingInterceptor()
    }


    private fun enabledTLS(client: OkHttpClient.Builder): OkHttpClient.Builder {
        if (isLollipopOrMinor()) {
            tryOrPrintException {
                val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers = trustManagerFactory.trustManagers
                if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                    throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
                }
                val trustManager = trustManagers[0] as X509TrustManager

                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
                val sslSocketFactory = sslContext.socketFactory
                client.sslSocketFactory(Tls12SocketFactory(sslSocketFactory), trustManager)

                val connectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build()

                val specs = mutableListOf<ConnectionSpec>(connectionSpec)
                client.connectionSpecs(specs)
            }
        }
        return client
    }

    @JvmStatic
    fun retrofitBuilder(
            httpClient: OkHttpClient,
            path: String,
            genericError: String,
            networkError: String,
            gson: Gson
    ): Retrofit.Builder {
        return Retrofit.Builder()
                .client(httpClient)
                .baseUrl(path)
                .addConverterFactory(QualifiedTypeConverterFactory(
                        GsonConverterFactory.create(gson),
                        SimpleXmlConverterFactory.createNonStrict(Persister(AnnotationStrategy()))))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactoryKt.create(genericError, networkError))
    }

    /*@Provides
    @JvmStatic
    fun certificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
                .add("*.grability.rappi.com",
                        "sha256/Exihh42Up0lX9xY5RaKgZcsKOAmhEarfV1GST6sdsPg=")
                .add("*.rappi.com.br",
                        "sha256/SyhZe64y6QWYTOO/r6wGXmCUsvORh2uEqXaMIkMqJ3M=")
                .add("*.rappi.cl",
                        "sha256/2Q80RZ6lQHxCEitT5lhpLhzpUp6/szEGRMeMZ2JgW18=")
                .add("*.rappi.pe",
                        "sha256/5qYSvNdVcNfE2hG8OhsYsu+wK65ZsQA0gI8ANr0KkpE=")
                .add("*.rappi.com.uy",
                        "sha256/IPsK3xJ4je7HdTgFF/FPSXuxUqk8khNaYZ4qMK5OUoI=")
                .add("*.rappi.com.ar",
                        "sha256/kkCul9vE9JcznF3hrzGHWtT79QkiJnQS6jRZ0Ifpoyo=")
                .add("*.mxgrability.rappi.com",
                        "sha256/bWj+IwjN/lTO9F4OK5i8nWDLhCoM4L43t/lKochlYPk=")
                //Backup certificate
                .add("*.grability.rappi.com",
                        "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
                .add("*.rappi.com.br",
                        "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
                .add("*.rappi.cl",
                        "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
                .add("*.rappi.pe",
                        "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
                .add("*.rappi.com.uy",
                        "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
                .add("*.rappi.com.ar",
                        "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
                .add("*.mxgrability.rappi.com",
                        "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
                .build()
    }*/

    @JvmStatic
    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> LogUtil.e("SERVER", message) }
                .apply { level = if (BaseConfig.SERVICE_LOGS_FLAG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }
    }

}
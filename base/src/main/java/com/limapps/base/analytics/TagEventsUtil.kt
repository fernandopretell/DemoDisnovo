package com.limapps.base.analytics

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.appboy.Appboy
import com.appboy.models.outgoing.AppboyProperties
import com.crashlytics.android.Crashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.limapps.base.BaseConfig
import com.limapps.base.analytics.handlers.AmplitudeHandler
//import com.limapps.base.analytics.handlers.AppsFlyerHandler
//import com.limapps.base.analytics.handlers.RakamHandler
import com.limapps.base.analytics.model.AnalyticsEvent
import com.limapps.base.analytics.model.AnalyticsEvent.ADDRESS_ID
import com.limapps.base.analytics.model.AnalyticsEvent.APPS
import com.limapps.base.analytics.model.AnalyticsEvent.COUNTRY
import com.limapps.base.analytics.model.AnalyticsEvent.FIREBASE_ERROR
import com.limapps.base.analytics.model.AnalyticsEvent.INSTALLED_APPS
import com.limapps.base.analytics.model.AnalyticsEvent.PHONE
import com.limapps.base.analytics.model.AnalyticsEvent.STORE_NAME
import com.limapps.base.analytics.model.AnalyticsEvent.USER
import com.limapps.base.analytics.model.AnalyticsEvent.USER_EMAIL
import com.limapps.base.analytics.model.AnalyticsEvent.USER_FULLNAME
import com.limapps.base.analytics.model.AnalyticsEvent.USER_ID
import com.limapps.base.analytics.model.AnalyticsEvent.VIEW_STORE_TYPE
import com.limapps.base.analytics.model.AppsFlyerEvent
import com.limapps.base.analytics.model.FacebookEvent
import com.limapps.base.models.Address
import com.limapps.base.models.UserInfoModel
import com.limapps.base.models.getCityName
import com.limapps.base.models.getFullName
import com.limapps.base.others.ResourcesProvider
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.utils.CountryUtil
import com.limapps.base.utils.LogUtil
import com.limapps.common.stringToArray
import com.limapps.common.tryOrPrintException
import org.json.JSONObject
import siftscience.android.Sift
import java.io.PrintWriter
import java.io.StringWriter

@Deprecated("Use analytics module instead")
object TagEventsUtil {

    val currencyCode: String
        get() = CountryUtil.getCurrencyCode()

    val gson by lazy { Gson() }

    private var application: Application? = null

    private val remoteConfig by lazy { FirebaseRemoteConfig.getInstance() }

    private val enabledBrazeEvents: Array<String>

    private var brazeEnabled = false

    init {
        brazeEnabled = remoteConfig.getValue("enable_braze_analytics").asBoolean()
        val eventsString = remoteConfig.getValue("braze_analytic_events").asString()
        enabledBrazeEvents = eventsString.stringToArray()
    }

    fun init(application: Application) {
        //AppsFlyerHandler.init(application)
        AmplitudeHandler.init(application)
        //RakamHandler.init(application)
        this.application = application
    }

    fun initUser(user: UserInfoModel) {

        val userId = CountryUtil.getCountryCode() + "_" + user.id

        AmplitudeHandler.setUserId(userId)
        //AppsFlyerHandler.setUserId(userId)
        //RakamHandler.setUserId(userId)
        //Sift.setUserId(user.id)
    }

    fun logFirebaseError(user: UserInfoModel?) {
        logEventWithParams(FIREBASE_ERROR, HashMap<String, String>()
                .apply {
                    if (user != null) {
                        put(USER, user.email)
                        put(PHONE, user.phone.orEmpty())
                    }
                })
    }

    fun updateUser() {
        PreferencesManager.userProfile?.let { initUser(it) }
    }

    fun logInstalledApps(application: Context, filter: String) {
        tryOrPrintException {
            val pm = application.packageManager

            val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)

            if (packages != null && packages.isNotEmpty()) {

                val apps = ArrayList<String>()

                val values = filter.split(",")

                packages.forEach {
                    if (it.packageName != null && values.contains(it.packageName)) {
                        apps.add(it.packageName)
                    }
                }

                if (apps.isNotEmpty()) {
                    logEventWithParams(INSTALLED_APPS, HashMap<String, String>()
                            .apply { put(APPS, apps.joinToString()) })
                }

            }
        }
    }

    @Deprecated("implement module analytics", ReplaceWith("logEventWithParams", "module analytics"))
    fun logEvent(event: String) = logEventWithParams(event, HashMap())

    @Deprecated("implement module analytics")
    fun logEventWithParamsHighDemand(event: String, params: MutableMap<String, String>, additionalValue: Double = 0.0) {
        logEventWithParams(event, params, additionalValue, true)
    }

    @Deprecated("implement module analytics")
    fun logEventWithParams(event: String, params: MutableMap<String, String>) {
        logEventWithParams(event, params, 0.0, false)
    }

    @Deprecated("implement module analytics")
    fun logEventWithParams(event: String, params: MutableMap<String, String>, additionalValue: Double) {
        logEventWithParams(event, params, additionalValue, false)
    }

    fun logEvent(facebookEvent: FacebookEvent) = Unit // Use AnalyticsAction instead [FB funnel does not log custom events anymore]

    fun logEvent(appsFlyerEvent: AppsFlyerEvent) {
        if (BaseConfig.ANALYTICS_FLAG) {
            //AppsFlyerHandler.logEvent(appsFlyerEvent)
        }
    }


    fun getUserAsMap(user: UserInfoModel?): Map<String, String> {
        return HashMap<String, String>(4).apply {
            user?.let {
                put(USER_EMAIL, user.email)
                put(USER_FULLNAME, user.getFullName())
                put(USER_ID, user.id)
            }
        }
    }


    fun getAddressAsMap(address: Address?): Map<String, String> {
        return HashMap<String, String>().apply {
            address?.apply {
                put(ADDRESS_ID, "$id")
                put(AnalyticsEvent.ADDRESS, this.address.toString())
                put(AnalyticsEvent.ADDRESS_DESCRIPTION, this.description.orEmpty())
                put(AnalyticsEvent.ADDRESS_TAG, this.tag ?: "")
                put(AnalyticsEvent.LAT, "$latitude")
                put(AnalyticsEvent.LNG, "$longitude")
                put(AnalyticsEvent.MICROZONE_ID, "${zone?.id}")
                put(AnalyticsEvent.MICROZONE_NAME, "${zone?.name}")
                put(COUNTRY, CountryUtil.getCurrentCountryName(ResourcesProvider))
                put(AnalyticsEvent.CITY, getCityName())

            }

        }
    }

    fun getBasicMap(key: String, value: String): MutableMap<String, String> {
        return mutableMapOf(Pair(key, value))
    }

    private fun getMapAsJson(map: Map<String, String>?): JSONObject {
        return JSONObject().apply {
            tryOrPrintException {
                map?.forEach { (key, value) ->
                    put(key, value)
                }
            }
        }
    }

    private fun getMapAsBundle(map: Map<String, String>?): Bundle {
        return Bundle(map?.size ?: 1).apply {
            map?.forEach { (key, value) ->
                putString(key, value)
            }
        }
    }

    private fun logEventWithParams(event: String, params: MutableMap<String, String>?, additionalValue: Double, highDemand: Boolean) {
        val mutableMap = params ?: mutableMapOf()

        if (BaseConfig.ANALYTICS_FLAG) {

            mutableMap.apply {

                logUserData(this)

                logCurrentAddressData(this)

                tryOrPrintException {
                    Crashlytics.log(Log.INFO, event, toMap().toString())
                }
                logEvent(FacebookEvent(event, additionalValue, getMapAsBundle(this)))
                //RakamHandler.logEvent(event, getMapAsJson(this))

                if (brazeEnabled && enabledBrazeEvents.contains(event)) {
                    val eventProperties = AppboyProperties()
                    params?.let { keys.forEach { eventProperties.addProperty(it, params[it]) } }
                    Appboy.getInstance(application).logCustomEvent(event, eventProperties)
                }
            }
        }

        if (BaseConfig.AMPLITUDE_FLAG && !highDemand) {
            AmplitudeHandler.logEvent(event, getMapAsJson(mutableMap))
        }
    }

    private fun logUserData(params: MutableMap<String, String>) {
        if (PreferencesManager.logIn().get()) {
            params.putAll(getUserAsMap(PreferencesManager.userProfile))
        }
    }

    private fun logCurrentAddressData(params: MutableMap<String, String>) {
        if (!params.containsKey(ADDRESS_ID)) {
            PreferencesManager.getCurrentAddress()?.let {
                params.putAll(getAddressAsMap(it))
            }
        }
    }

    fun logViewStoreType(storeType: String, source: String? = null, productsInCart: Boolean = false) {
        val params = mutableMapOf<String, String>().apply {
            put(AnalyticsEvent.STORE_TYPE, storeType)
            put(AnalyticsEvent.PRODUCTS_IN_CART, productsInCart.toString())
            source?.let { put(AnalyticsEvent.SOURCE, it) }
            putAll(getUserAsMap(PreferencesManager.userProfile))
        }
        TagEventsUtil.logEventWithParams(VIEW_STORE_TYPE, params)
    }

    fun logServerError(errorMap: MutableMap<String, String>) {
        if (BaseConfig.ANALYTICS_FLAG) {
            logUserData(errorMap)
            logCurrentAddressData(errorMap)
            AmplitudeHandler.logEvent(AnalyticsEvent.SERVER_ERROR, getMapAsJson(errorMap))
            //RakamHandler.logEvent(AnalyticsEvent.SERVER_ERROR, getMapAsJson(errorMap))
            tryOrPrintException {
                Crashlytics.log(Log.ERROR, AnalyticsEvent.SERVER_ERROR, errorMap.toMap().toString())
            }
        }
    }

    fun logServerUnexpectedError(errorMap: MutableMap<String, String>, throwable: Throwable? = null) {
        if (BaseConfig.ANALYTICS_FLAG) {
            tryOrPrintException {
                throwable?.let {
                    val stringWriter = StringWriter()
                    it.printStackTrace(PrintWriter(stringWriter))
                    val exceptionAsString = stringWriter.toString()
                    AmplitudeHandler.logEvent(AnalyticsEvent.SERVER_UNEXPECTED_ERROR, getMapAsJson(errorMap))
                    //RakamHandler.logEvent(AnalyticsEvent.SERVER_UNEXPECTED_ERROR, getMapAsJson(errorMap))

                    Crashlytics.log(Log.ERROR, AnalyticsEvent.SERVER_UNEXPECTED_ERROR, exceptionAsString)
                }
            }
        } else {
            LogUtil.e(AnalyticsEvent.SERVER_UNEXPECTED_ERROR, "CHECK THIS", throwable)
        }
    }

    fun logServerNetworkError(errorMap: MutableMap<String, String>) {
        if (BaseConfig.ANALYTICS_FLAG) {
            tryOrPrintException {
                AmplitudeHandler.logEvent(AnalyticsEvent.SERVER_NETWORK_ERROR, getMapAsJson(errorMap))
                //RakamHandler.logEvent(AnalyticsEvent.SERVER_NETWORK_ERROR, getMapAsJson(errorMap))
                Crashlytics.log(Log.ERROR, AnalyticsEvent.SERVER_NETWORK_ERROR, errorMap.toMap().toString())
            }
        }
    }

}

data class BaseAnalyticStore(val name: String, val type: String, val id: String)

fun BaseAnalyticStore.toMap(): Map<String, String> {
    return HashMap<String, String>(3).let {
        it[AnalyticsEvent.STORE_ID] = id
        it[AnalyticsEvent.STORE_TYPE] = type
        it[STORE_NAME] = name
        it
    }
}

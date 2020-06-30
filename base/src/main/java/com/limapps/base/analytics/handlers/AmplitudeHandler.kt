package com.limapps.base.analytics.handlers

import android.app.Application

import com.amplitude.api.Amplitude
import com.amplitude.api.AmplitudeClient
import com.limapps.base.BaseConfig
import com.limapps.base.R
import com.limapps.base.managers.FileManager
import com.limapps.base.others.ResourcesProvider

import org.json.JSONObject

object AmplitudeHandler {

    private val amplitudeClient: AmplitudeClient by lazy { Amplitude.getInstance() }

    fun init(application: Application) {

        val amplitudeKey = if (BaseConfig.AMPLITUDE_DEV) {
            ResourcesProvider.getString(R.string.api_key_amplitude_dev)
        } else {
            ResourcesProvider.getString(R.string.api_key_amplitude)
        }
        amplitudeClient.initialize(application, amplitudeKey)
                .enableForegroundTracking(application)

        if (BaseConfig.ANALYTICS_CUSTOM_CONFIG) {
            amplitudeClient.setEventUploadPeriodMillis(1000)
        }
    }

    fun setUserId(userId: String) {
        amplitudeClient.userId = userId
    }

    fun logEvent(event: String?, params: JSONObject?) {
        if (event != null && params != null) {
            amplitudeClient.logEvent(event, params)
            if (BaseConfig.ANALYTICS_FILE_LOGGER) {
                FileManager.logAnalyticsEventToFile(event, params)
            }
        }
    }
}

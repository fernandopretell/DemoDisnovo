package com.limapps.base.repositories

import com.limapps.base.others.RappiData
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.rest.constants.HttpConstants

import java.util.HashMap

open class BaseRepository {

    companion object {

        fun getAuthHeaders(): Map<String, String> {
            val headers = HashMap<String, String>()
            val accessToken = PreferencesManager.accessToken().get()
            return if (accessToken.isNotEmpty()) {
                headers.put(HttpConstants.AUTHORIZATION, HttpConstants.BEARER + " " + accessToken)
                headers.put(HttpConstants.DEVICE_ID, RappiData.androidID)
                headers
            } else {
                emptyMap()
            }
        }

        fun getFCMHeaders(): Map<String, String> {
            RappiData.fcmToken?.let {
                val headers = HashMap<String, String>()
                headers.put(HttpConstants.TOKEN_FCM, it)
                return headers
            } ?: kotlin.run {
                return emptyMap()
            }
        }
    }
}

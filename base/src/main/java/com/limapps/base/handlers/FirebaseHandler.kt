package com.limapps.base.handlers

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.limapps.base.models.UserInfoModel
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.utils.LogUtil
import com.limapps.common.isNotNullOrBlank

const val CLIENT_CHANNEL = "rappi_client_"
private const val BROADCAST_CHANNEL = "rappi_client"
private const val PAY_CHANNEL = "rappi_pay_"
private const val REWARDS_CHANNEL = "rewards_"
private const val APP_VERSION = "app_version"
private const val GO_TAXI = "go-taxi"

class FirebaseHandler(private val googlePlayServicesController: GooglePlayServicesController,
                      private val context: Context) {

    fun registerClientChannel(user: UserInfoModel?) {

        user?.let {
            initialize(user.id)
            if (user.id.isNotNullOrBlank()) {
                subscribeTopic(CLIENT_CHANNEL + user.id)
            }
        }

    }

    fun registerOrderTopic(userId: String, orderId: String) {
        val channel = CLIENT_CHANNEL + userId + "_" + orderId
        subscribeTopic(channel)
    }

    fun unregisterOrderTopic(userId: String, orderId: String?) {
        val channel = CLIENT_CHANNEL + userId + "_" + orderId
        unsubscribeTopic(channel)
    }

    fun unsubscribeClientTopic(userId: String) {
        val simpleTopic = CLIENT_CHANNEL + userId
        val topicWithCountry = getTopicWithCountry(simpleTopic)
        unsubscribeTopic(topicWithCountry)
        unsubscribeTopic(getPayTopic(userId))
    }

    fun registerRewardsChannel(userId: String) {
        subscribeTopic(REWARDS_CHANNEL + userId)
    }

    fun registerBaseChannel() {
        subscribeTopic(BROADCAST_CHANNEL)
    }

    private fun subscribeTopic(registerChannel: String) {
        val topicWithCountry = getTopicWithCountry(registerChannel)
        if (this.googlePlayServicesController.isGooglePlayServicesAvailable())
            FirebaseMessaging.getInstance().subscribeToTopic(topicWithCountry)
    }

    private fun unsubscribeTopic(topicToUnsubscribe: String) {
        if (this.googlePlayServicesController.isGooglePlayServicesAvailable())
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topicToUnsubscribe)
    }

    private fun initialize(userId: String) {
        registerRappiPayTopic(userId)
        registerGoTaxiTopic(userId)
    }

    private fun getPayTopic(userId: String): String {
        return PAY_CHANNEL + PreferencesManager.countryCodeData().get() + "_AND_" + userId + "_"
    }

    private fun getGoTaxiTopic(userId: String): String {
        return PreferencesManager.countryCodeData().get() + "_" + GO_TAXI + "_" + userId
    }

    private fun registerRappiPayTopic(userId: String) {
        if (this.googlePlayServicesController.isGooglePlayServicesAvailable())
            FirebaseMessaging.getInstance().subscribeToTopic(getPayTopic(userId))
    }

    private fun registerGoTaxiTopic(userId: String) {
        if (this.googlePlayServicesController.isGooglePlayServicesAvailable()) {
            FirebaseMessaging.getInstance().subscribeToTopic(getGoTaxiTopic(userId))
        }
    }

    private fun getTopicWithCountry(simpleTopic: String): String {
        var registerTopicWithCountry = PreferencesManager.countryCodeData().get()

        if (registerTopicWithCountry.isNotEmpty()) {
            registerTopicWithCountry += "_$simpleTopic"
        } else {
            registerTopicWithCountry = simpleTopic
        }

        return registerTopicWithCountry
    }

    fun singOutFirebase() {
        try {
            if (FirebaseAuth.getInstance().currentUser != null) {
                FirebaseAuth.getInstance().signOut()
            }
        } catch (exception: Exception) {
            LogUtil.e("Firebase", "error logging out user")
        }
    }
}
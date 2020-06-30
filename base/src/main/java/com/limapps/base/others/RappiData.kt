package com.limapps.base.others

object RappiData {

    lateinit var appVersion: String
    lateinit var androidID: String
    lateinit var deviceOs: String
    lateinit var deviceModel: String
    lateinit var appVersionName: String
    lateinit var freeMemory: String
    lateinit var totalMemory: String
    lateinit var networkType: String
    lateinit var networkCarrier: String
    var fcmToken: String? = null
    @Volatile
    var advertiserID: String? = null
}
package com.limapps.base.uxcam

interface UxCamAnalytics {
    fun setUserId(userId: String, userProperties: Map<String,String> = HashMap())

    fun log(event: String, params: Map<String, String>)
}
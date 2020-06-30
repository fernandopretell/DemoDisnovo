package com.limapps.base.notifications

import android.content.Context
import android.os.Bundle

interface PushNotificationController {

    fun show(context: Context, activityClass: Class<*>, pushNotification: PushNotification, extras: Bundle? = null)

    fun showWithAction(context: Context, activityClass: Class<*>, title: String, message: String?, image: String?, deepLink: String)
}
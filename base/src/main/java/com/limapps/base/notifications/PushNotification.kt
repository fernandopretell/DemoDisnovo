package com.limapps.base.notifications

import com.limapps.base.R

open class PushNotification(
        val id: Int,
        val icon: Int = R.drawable.ic_push,
        val message: String,
        val title: String,
        val image: String = "",
        var sound: Int = -1
)
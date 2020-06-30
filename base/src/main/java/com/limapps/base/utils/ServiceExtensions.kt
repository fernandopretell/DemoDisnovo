package com.limapps.base.utils

import android.app.Activity
import android.content.Intent
import androidx.core.content.ContextCompat
import com.limapps.common.isOreo

fun Activity.startServiceCompat(intent: Intent) {
//    if (isOreo()) {
//        ContextCompat.startForegroundService(this, intent)
//    } else {
        startService(intent)
//    }
}
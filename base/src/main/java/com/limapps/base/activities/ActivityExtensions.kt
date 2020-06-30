package com.limapps.base.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.limapps.common.hideKeyboard

fun Activity.presentActivity(intent: Intent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val pairs = TransitionHelper.createSafeTransitionParticipants(this, true)
        val transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pairs)
        startActivity(intent, transitionActivityOptions.toBundle())
    } else {
        startActivity(intent)
    }
}

fun Activity.presentActivityForResult(intent: Intent, requestCode: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val pairs = TransitionHelper.createSafeTransitionParticipants(this, true)
        val transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pairs)
        startActivityForResult(intent, requestCode, transitionActivityOptions.toBundle())
    } else {
        startActivityForResult(intent, requestCode)
    }
}

fun Activity.hideKeyboardEx() = hideKeyboard(this)

fun FragmentActivity.setStatusBarLight(@ColorInt statusBarColor: Int = Color.WHITE) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = window.decorView.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.decorView.systemUiVisibility = flags
        window.statusBarColor = statusBarColor
    }
}

fun FragmentActivity.setStatusBarDark(@ColorInt statusBarColor: Int = Color.BLACK) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = window.decorView.systemUiVisibility
        flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        window.decorView.systemUiVisibility = flags
        window.statusBarColor = statusBarColor
    }
}

fun FragmentActivity.setNavigationBarLight(@ColorInt navBarColor: Int = Color.WHITE) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        var flags = window.decorView.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        window.decorView.systemUiVisibility = flags
        window.navigationBarColor = navBarColor
    }
}

fun FragmentActivity.setNavigationBarDark(@ColorInt navBarColor: Int = Color.BLACK) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        var flags = window.decorView.systemUiVisibility
        flags = flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        window.decorView.systemUiVisibility = flags
        window.navigationBarColor = navBarColor
    }
}

fun FragmentActivity.setNavigationBarDividerColor(@ColorInt navBarDividerColor: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        window.navigationBarDividerColor = navBarDividerColor
    }
}


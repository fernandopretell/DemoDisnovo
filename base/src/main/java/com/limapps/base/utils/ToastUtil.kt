package com.limapps.base.utils

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.limapps.base.R
import com.limapps.common.setDrawableLeft

const val ALERT_ERROR = 0
const val ALERT_SUCCESSFUL = 1
const val ALERT_BLACK = 2
const val TOAST_OFFSET_MILLIS = 1000

object ToastUtil {

    private var lastShown: String = ""

    private var lastTimeShowed = System.currentTimeMillis()

    private fun isRecent(message: String): Boolean = message == lastShown

    @Synchronized
    private fun showAlert(context: Context, message: String, typeAlert: Int, contentDescription: String? = null) {
        val currentTimeMillis = System.currentTimeMillis()
        if (!isRecent(message) || currentTimeMillis - lastTimeShowed > TOAST_OFFSET_MILLIS) {
            showToast(context.applicationContext, createAlert(context, message, typeAlert, contentDescription))
            lastShown = message
            lastTimeShowed = currentTimeMillis
        }
    }

    fun showSuccessfulAlert(activity: Activity, message: String) {
        (activity.window?.decorView?.rootView as? ViewGroup)?.let {
            showAlert(it.context, message, ALERT_SUCCESSFUL)
        }
    }

    fun showBlackAlert(activity: Activity, message: String) {
        (activity.window?.decorView?.rootView as? ViewGroup)?.let {
            showAlert(it.context, message, ALERT_BLACK)
        }
    }

    fun showSuccessfulAlert(activity: Activity, @StringRes resource: Int) {
        (activity.window?.decorView?.rootView as? ViewGroup)?.let {
            showAlert(it.context, it.context.getString(resource), ALERT_SUCCESSFUL)
        }
    }

    fun showErrorAlert(activity: Activity, message: String) {
        (activity.window?.decorView?.rootView as? ViewGroup)?.let {
            showAlert(it.context, message, ALERT_ERROR)
        }
    }

    fun showErrorAlert(activity: Activity, @StringRes resource: Int) {
        (activity.window?.decorView?.rootView as? ViewGroup)?.let {
            showAlert(it.context, it.context.getString(resource), ALERT_ERROR)
        }
    }

    fun showErrorAlert(context: Context, @StringRes resource: Int) {
        showAlert(context, context.getString(resource), ALERT_ERROR)
    }

    fun showErrorAlert(context: Context, message: String, contentDescription: String? = null) {
        showAlert(context, message, ALERT_ERROR, contentDescription)
    }

    fun showErrorAlert(fragment: androidx.fragment.app.Fragment, errorMessage: String) {
        (fragment.view as? ViewGroup)?.let {
            showAlert(it.context, errorMessage, ALERT_ERROR)
        }
    }

    fun showErrorAlert(fragment: androidx.fragment.app.Fragment, @StringRes resource: Int) {
        (fragment.view as? ViewGroup)?.let {
            showAlert(it.context, it.context.getString(resource), ALERT_ERROR)
        }
    }

    private fun createAlert(context: Context, message: String, typeAlert: Int, contentDescription: String? = null): View {

        val view = LayoutInflater.from(context).inflate(R.layout.view_toast_alert, null)

        contentDescription?.let { view.contentDescription = it }

        val textView = view.findViewById<TextView>(R.id.textView_toast)

        textView.text = message

        val bg = when (typeAlert) {
            ALERT_ERROR -> R.drawable.bg_round_red
            ALERT_SUCCESSFUL -> R.drawable.bg_round_green
            ALERT_BLACK -> R.drawable.bg_round_black
            else -> R.drawable.bg_round_red
        }

        val icon = when (typeAlert) {
            ALERT_BLACK -> R.drawable.ic_toast_alert_red
            else -> null
        }

        textView.setBackgroundResource(bg)

        if (icon != null) {
            textView.setDrawableLeft(ContextCompat.getDrawable(context, icon), 0)
        }
        return view
        
    }

    private fun showToast(context: Context, view: View) {
        Handler(Looper.getMainLooper()).post {
            val toast = Toast(context)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.duration = Toast.LENGTH_LONG
            toast.view = view
            toast.show()
        }
    }
}

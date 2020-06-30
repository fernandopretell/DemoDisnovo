package com.limapps.base.handlers

import android.app.Activity

interface GooglePlayServicesController {

    fun isGooglePlayServicesAvailable(): Boolean

    fun showDialog(activity: Activity, requestCode: Int): Boolean

}
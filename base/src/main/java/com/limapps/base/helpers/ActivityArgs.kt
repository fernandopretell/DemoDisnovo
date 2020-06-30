package com.limapps.base.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.limapps.base.others.ExtrasKeys.LAUNCH_FOR_RESULT

interface ActivityArgs {

    fun intent(context: Context): Intent

    fun launch(context: Context) = context.startActivity(intent(context))

    fun launchForResult(activity: Activity) = activity.startActivityForResult(intent(activity), LAUNCH_FOR_RESULT)
}
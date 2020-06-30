package com.limapps.base.utils.logger

import android.util.Log

import com.crashlytics.android.Crashlytics
import com.limapps.base.BaseConfig
import com.limapps.common.tryOrPrintException
import java.lang.Exception

object CrashlyticsLogger {

    fun logResumeActivity(msg: String) {
        tryOrPrintException {
            if (BaseConfig.ANALYTICS_FLAG) {
                Crashlytics.log(Log.INFO, "Activity resumed", msg)
            }
        }
    }

    fun logOnPauseActivity(msg: String) {
        tryOrPrintException {
            if (BaseConfig.ANALYTICS_FLAG) {
                Crashlytics.log(Log.INFO, "Activity paused", msg)
            }
        }
    }

    fun logOnCreateActivity(msg: String) {
        tryOrPrintException {
            if (BaseConfig.ANALYTICS_FLAG) {
                Crashlytics.log(Log.INFO, "Activity created", msg)
            }
        }
    }

    fun logOnDestroyActivity(msg: String) {
        tryOrPrintException {
            if (BaseConfig.ANALYTICS_FLAG) {
                Crashlytics.log(Log.INFO, "Activity destroyed", msg)
            }
        }
    }

    fun logCrash(tag: String, msg: String, key: String, value: String, priority: Int = Log.ERROR, throwable: Throwable? = null) {
        tryOrPrintException {
            Crashlytics.setString(key, value)
            Crashlytics.log(priority, tag, msg)
            throwable?.let {
                Crashlytics.logException(throwable)
            }
        }
    }
}

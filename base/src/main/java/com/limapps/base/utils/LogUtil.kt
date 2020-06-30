package com.limapps.base.utils

import android.os.Looper
import android.util.Log
import com.limapps.base.BaseConfig
import com.limapps.common.tryOrPrintException

object LogUtil {

    @JvmStatic
    private val isDebug = BaseConfig.APP_LOGS_FLAG

    private fun getMessageWithPrefix(msg: String?): String {
        val currentThread = Thread.currentThread()
        val isMainThread = currentThread == Looper.getMainLooper().thread

        return "(${currentThread.name}#$isMainThread): $msg"
    }

    @JvmStatic
    fun e(tag: String, msg: String?) {
        doSomethingOnDebug {
            Log.e(tag, getMessageWithPrefix(msg))
        }
    }

    @JvmStatic
    fun e(tag: String, msg: String? = null, throwable: Throwable? = null) {
        doSomethingOnDebug {
            if (msg != null || throwable != null) {
                Log.e(tag, getMessageWithPrefix(msg), throwable)
            }
        }
    }

    @JvmStatic
    fun i(tag: String, msg: String?) {
        doSomethingOnDebug {
            Log.i(tag, getMessageWithPrefix(msg))
        }
    }

    @JvmStatic
    fun w(tag: String, msg: String?) {
        doSomethingOnDebug {
            Log.w(tag, getMessageWithPrefix(msg))
        }
    }

    @JvmStatic
    fun d(tag: String, msg: String?) {
        doSomethingOnDebug {
            Log.d(tag, getMessageWithPrefix(msg))
        }
    }

    @JvmStatic
    fun d(tag: String, msg: String?, throwable: Throwable) {
        doSomethingOnDebug {
            Log.d(tag, getMessageWithPrefix(msg), throwable)
        }
    }

    private fun <T> doSomethingOnDebug(bodyFunction: () -> T) {
        tryOrPrintException {
            if (isDebug) {
                bodyFunction()
            }
        }
    }
}

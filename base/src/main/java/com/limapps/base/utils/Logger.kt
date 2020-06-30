package com.limapps.base.utils

interface Logger {

    fun e(tag: String, msg: String? = null, throwable: Throwable? = null)

    fun i(tag: String, msg: String? = null)

    fun w(tag: String, msg: String? = null)

    fun d(tag: String, msg: String? = null, throwable: Throwable? = null)
}
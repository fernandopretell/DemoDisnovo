package com.limapps.base.utils

import android.os.Bundle

class BundleService(savedState: Bundle?, intentExtras: Bundle?) {
    private val data: Bundle = Bundle()

    init {
        savedState?.let {
            data.putAll(it)
        }
        intentExtras?.let {
            data.putAll(it)
        }
    }

    operator fun get(key: String): Any? {
        return data.get(key)
    }

    operator fun contains(key: String): Boolean {
        return data.containsKey(key)
    }

    fun getAll() = data
}
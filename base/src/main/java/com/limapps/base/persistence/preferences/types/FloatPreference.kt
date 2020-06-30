package com.limapps.base.persistence.preferences.types

import android.content.SharedPreferences
import com.limapps.base.persistence.preferences.PreferencesBase

class FloatPreference
/**
 * Constructs a `float` preference for the given key
 * having the default value available.
 */
@JvmOverloads constructor(private val preferences: SharedPreferences,
                          private val key: String,
                          private val defaultValue: Float = DEFAULT_VALUE) {

    /**
     * Returns `true` if some value is stored for
     * this preference, otherwise `false`.
     */
    val isSet: Boolean
        get() = preferences.contains(key)

    /**
     * Returns the stored `float` value if it exists
     * or the default value.
     */
    fun get(): Float {
        try {
            return preferences.getFloat(key, defaultValue)
        } catch (e: Exception) {
            e.printStackTrace()
            return preferences.getInt(key, defaultValue.toInt()).toFloat()
        }

    }

    /**
     * Stores the given `float` value.
     */
    fun set(value: Float) {
        preferences.edit().putFloat(key,value).apply()
    }

    /**
     * Removes this preference setting.
     */
    fun delete() {
        preferences.edit().remove(key).apply()
    }

    companion object {
        const val DEFAULT_VALUE = 0f
    }

}
/**
 * Constructs a `float` preference for the given key
 * having the default value set to `0` available.
 */

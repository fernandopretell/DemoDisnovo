package com.limapps.base.persistence.preferences.types

import android.content.SharedPreferences

class StringPreference
/**
 * Constructs a `String` preference for the given key
 * having the default value available.
 */
@JvmOverloads constructor(private val preferences: SharedPreferences,
                          private val key: String,
                          private val defaultValue: String? = DEFAULT_VALUE) {

    /**
     * Returns `true` if some value is stored for
     * this preference, otherwise `false`.
     */
    val isSet: Boolean
        get() = preferences.contains(key)

    /**
     * Returns the stored `String` value if it exists
     * or the default value.
     */
    fun get(): String {
        return preferences.getString(key, defaultValue).orEmpty()
    }

    /**
     * Stores the given `String` value.
     */
    fun set(value: String?) {
        if (value == null) {
            preferences.edit().remove(key).apply()
        } else {
            preferences.edit().putString(key,value).apply()
        }
    }

    /**
     * Removes this preference setting.
     */
    fun delete() {
        preferences.edit().remove(key).apply()
    }

    companion object {
        val DEFAULT_VALUE: String? = null
    }

}
/**
 * Constructs a `String` preference for the given key
 * having the default value set to an empty string available.
 */

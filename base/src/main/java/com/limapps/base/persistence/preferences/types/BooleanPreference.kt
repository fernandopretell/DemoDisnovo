package com.limapps.base.persistence.preferences.types

import android.content.SharedPreferences
import com.limapps.base.persistence.preferences.PreferencesBase

/**
 * Created by juanpinto on 25/04/16.
 */
class BooleanPreference
/**
 * Constructs a `double` preference for the given key
 * having the default value available.
 */
@JvmOverloads constructor(private var preferences: SharedPreferences,
                          private var key: String,
                          private var defaultValue: Boolean = DEFAULT_VALUE) {

    /**
     * Returns `true` if some value is stored for
     * this preference, otherwise `false`.
     */
    val isSet: Boolean
        get() = preferences.contains(key)

    /**
     * Returns the stored `double` value if it exists
     * or the default value.
     */
    fun get(): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    /**
     * Stores the given `double` value.
     */
    fun set(value: Boolean) {
        preferences.edit().putBoolean(key,value).apply()
    }

    /**
     * Removes this preference setting.
     */
    fun delete() {
        preferences.edit().remove(key)
    }

    companion object {
        const val DEFAULT_VALUE = false
    }
}
/**
 * Constructs a `double` preference for the given key
 * having the default value set to zero available.
 */

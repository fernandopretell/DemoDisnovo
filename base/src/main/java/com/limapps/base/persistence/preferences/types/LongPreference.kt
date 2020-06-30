package com.limapps.base.persistence.preferences.types

import android.content.SharedPreferences
import com.limapps.base.persistence.preferences.PreferencesBase

/**
 * Created by juanpinto on 25/04/16.
 */
class LongPreference
/**
 * Constructs a `long` preference for the given key
 * having the default value available.
 */
@JvmOverloads constructor(private val preferences: SharedPreferences,
                          private val key: String,
                          private val defaultValue: Long = DEFAULT_VALUE) {

    /**
     * Returns `true` if some value is stored for
     * this preference, otherwise `false`.
     */
    val isSet: Boolean
        get() = preferences.contains(key)

    /**
     * Returns the stored `long` value if it exists
     * or the default value.
     */
    fun get(): Long {
        return preferences.getLong(key,defaultValue)
    }

    /**
     * Stores the given `long` value.
     */
    fun set(value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    /**
     * Removes this preference setting.
     */
    fun delete() {
        preferences.edit().remove(key).apply()
    }

    companion object {
        const val DEFAULT_VALUE = 0L
    }

}
/**
 * Constructs a `long` preference for the given key
 * having the default value set to `0` available.
 */

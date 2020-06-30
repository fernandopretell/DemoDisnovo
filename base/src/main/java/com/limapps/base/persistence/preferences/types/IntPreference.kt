package com.limapps.base.persistence.preferences.types

import android.content.SharedPreferences
import com.limapps.base.persistence.preferences.PreferencesBase

/**
 * Created by juanpinto on 25/04/16.
 */
class IntPreference
/**
 * Constructs a `int` preference for the given key
 * having the default value available.
 */
@JvmOverloads constructor(private val preferences: SharedPreferences,
                          private val key: String,
                          private val defaultValue: Int = DEFAULT_VALUE) {

    /**
     * Returns `true` if some value is stored for
     * this preference, otherwise `false`.
     */
    val isSet: Boolean
        get() = preferences.contains(key)

    /**
     * Returns the stored `int` value if it exists
     * or the default value.
     */
    fun get(): Int {
        return preferences.getInt(key,defaultValue)
    }

    /**
     * Stores the given `int` value.
     */
    fun set(value: Int) {
        preferences.edit().putInt(key,value).apply()
    }

    /**
     * Removes this preference setting.
     */
    fun delete() {
        preferences.edit().remove(key)
    }

    companion object {
        const val DEFAULT_VALUE = 0
    }

}
/**
 * Constructs a `int` preference for the given key
 * having the default value set to zero available.
 */

package com.limapps.base.persistence.preferences.types

import android.content.SharedPreferences
import com.limapps.base.persistence.preferences.PreferencesBase

/**
 * Created by juanpinto on 25/04/16.
 */
class DoublePreference
/**
 * Constructs a `double` preference for the given key
 * having the default value available.
 */
@JvmOverloads constructor(preferences: SharedPreferences,
                          key: String,
                          defaultValue: Double = DEFAULT_VALUE) {

    private var longPreference: LongPreference = LongPreference(preferences, key, java.lang.Double.doubleToLongBits(defaultValue))

    /**
     * Returns `true` if some value is stored for
     * this preference, otherwise `false`.
     */
    val isSet: Boolean
        get() = longPreference.isSet

    /**
     * Returns the stored `double` value if it exists
     * or the default value.
     */
    fun get(): Double {
        return java.lang.Double.longBitsToDouble(longPreference.get())
    }

    /**
     * Stores the given `double` value.
     */
    fun set(value: Double) {
        longPreference.set(java.lang.Double.doubleToLongBits(value))
    }

    /**
     * Removes this preference setting.
     */
    fun delete() {
        longPreference.delete()
    }

    companion object {
        val DEFAULT_VALUE = 0.0
    }

}
/**
 * Constructs a `double` preference for the given key
 * having the default value set to zero available.
 */

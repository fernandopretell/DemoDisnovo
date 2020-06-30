package com.limapps.base.persistence.preferences

import android.content.Context
import android.content.SharedPreferences
import com.limapps.base.persistence.preferences.types.BooleanPreference
import com.limapps.base.persistence.preferences.types.DoublePreference
import com.limapps.base.persistence.preferences.types.FloatPreference
import com.limapps.base.persistence.preferences.types.IntPreference
import com.limapps.base.persistence.preferences.types.LongPreference
import com.limapps.base.persistence.preferences.types.StringPreference
import com.securepreferences.SecurePreferences


open class PreferencesBase {

    protected lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context, fileName: String) {
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    fun initSecure(context: Context, fileName: String) {
        sharedPreferences = SecurePreferences(context, "", fileName)
    }

    operator fun set(key: String, value: String): PreferencesBase {
        sharedPreferences.edit().putString(key, value).apply()
        return this
    }

    operator fun get(key: String): String? = sharedPreferences.getString(key, null)

    fun getOr(key: String, or: String): String = sharedPreferences.getString(key, or).toString()

    operator fun set(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int = sharedPreferences.getInt(key, 0)

    operator fun set(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String): Float = sharedPreferences.getFloat(key, 0f)

    operator fun set(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean = sharedPreferences.getBoolean(key, defaultValue)

    fun set(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getLong(key: String): Long = sharedPreferences.getLong(key, -1)

    operator fun contains(key: String): Boolean = sharedPreferences.contains(key)

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun booleanPreference(key: String): BooleanPreference {
        return BooleanPreference(sharedPreferences, key)
    }

    fun booleanPreference(key: String, defaultValue: Boolean): BooleanPreference {
        return BooleanPreference(sharedPreferences, key, defaultValue)
    }

    fun stringPreference(key: String): StringPreference {
        return StringPreference(sharedPreferences, key)
    }

    fun stringPreference(key: String, defaultValue: String?): StringPreference {
        return sharedPreferences.let { StringPreference(it, key, defaultValue) }
    }

    fun longPreference(key: String): LongPreference = LongPreference(sharedPreferences, key)

    fun longPreference(key: String, defaultValue: Long): LongPreference {
        return LongPreference(sharedPreferences, key, defaultValue)
    }

    fun intPreference(key: String): IntPreference = IntPreference(sharedPreferences, key)

    fun intPreference(key: String, defaultValue: Int): IntPreference {
        return IntPreference(sharedPreferences, key, defaultValue)
    }

    fun doublePreference(key: String, defaultValue: Double): DoublePreference {
        return DoublePreference(sharedPreferences, key, defaultValue)
    }

    fun doublePreference(key: String): DoublePreference {
        return DoublePreference(sharedPreferences, key)
    }

    fun floatPreference(key: String, defaultValue: Float): FloatPreference {
        return FloatPreference(sharedPreferences, key, defaultValue)
    }

    fun floatPreference(key: String): FloatPreference {
        return FloatPreference(sharedPreferences, key)
    }

    fun getAll(): Map<String, String> {
        return sharedPreferences.all.mapValues { it.value as String }
    }

    fun deleteAll() {
        sharedPreferences.edit().clear().apply()
    }
}
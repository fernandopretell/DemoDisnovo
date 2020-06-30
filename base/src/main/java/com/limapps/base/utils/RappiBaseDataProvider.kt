package com.limapps.base.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.common.getStringOrDefault
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY = "key"
private const val VALUE = "value"

@Singleton
class RappiBaseDataProvider @Inject constructor(private val preferences: PreferencesManager,
                                                private val gson: Gson) : BaseDataProvider {

    private var jsonArray: JsonArray? = null

    override fun saveData(json: String) {
        jsonArray = null
        preferences.additionalData().set(json)
    }

    override fun cleanData() {
        jsonArray = null
        preferences.additionalData().set(StringUtils.EMPTY_JSON_ARRAY)
    }

    override fun getString(key: String, defaultValue: String): String {
        if (jsonArray == null) {
            loadData()
        }

        val jsonObject = jsonArray?.firstOrNull { matchesWithKey(it.asJsonObject, key) }?.asJsonObject
        return jsonObject?.getStringOrDefault(VALUE) ?: defaultValue
    }

    private fun loadData() {
        jsonArray = gson.fromJson(preferences.additionalData().get(), JsonArray::class.java)
    }

    private fun matchesWithKey(jsonObject: JsonObject, key: String): Boolean {
        return jsonObject.has(KEY) && jsonObject.has(VALUE) && jsonObject.getStringOrDefault(KEY) == key
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return getString(key, defaultValue.toString()).toBoolean()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return getString(key, defaultValue.toString()).toInt()
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return getString(key, defaultValue.toString()).toFloat()
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return getString(key, defaultValue.toString()).toDouble()
    }

    override fun getBooleanPreference(key: String): Boolean {
        return preferences.booleanPreference(key).get()
    }

    override fun setBooleanPreference(key: String, value: Boolean) {
        return preferences.booleanPreference(key).set(value)
    }

    override fun getIntPreference(key: String): Int {
        return preferences.intPreference(key).get()
    }

    override fun setIntPreference(key: String, value: Int) {
        return preferences.intPreference(key).set(value)
    }
}
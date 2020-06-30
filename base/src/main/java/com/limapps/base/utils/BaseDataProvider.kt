package com.limapps.base.utils

interface BaseDataProvider {
    fun saveData(json: String)
    fun cleanData()
    fun getString(key: String, defaultValue: String = ""): String
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun getInt(key: String, defaultValue: Int = 0): Int
    fun getFloat(key: String, defaultValue: Float = 0f): Float
    fun getDouble(key: String, defaultValue: Double = 0.0): Double
    fun getBooleanPreference(key: String): Boolean
    fun setBooleanPreference(key: String, value: Boolean = false)
    fun getIntPreference(key: String): Int
    fun setIntPreference(key: String, value: Int = 0)
}
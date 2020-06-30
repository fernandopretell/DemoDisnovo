package com.limapps.base.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException


fun <T> JsonElement?.fromJson(clazz: Class<T>): T? {
    try {
        return Gson().fromJson(JsonParser().parse(this.toString()), clazz)
    } catch (e: JsonSyntaxException) {
        return null
    }
}
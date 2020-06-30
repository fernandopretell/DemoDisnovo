package com.limapps.base.helpers

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.limapps.common.getJsonObjectOrDefault
import org.junit.Test

class GsonExtensionsTest {

    val gson by lazy { Gson() }

    val KEY = "key"
    val emptyJsonObject = JsonObject()
    val mimimumJsonString = "{\"key\":null}"
    val minimumJsonObject by lazy { gson.fromJson(mimimumJsonString, JsonObject::class.java) }

    @Test
    fun `Get null JsonObject when key does not exists`() {
        val jsonObject = emptyJsonObject.getJsonObjectOrDefault(KEY)

        assert(jsonObject == null)
    }

    @Test
    fun `Get null JsonObject when key exists and is null`() {
        val jsonObject = minimumJsonObject.getJsonObjectOrDefault(KEY)

        assert(jsonObject == null)
    }
}
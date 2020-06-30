package com.limapps.base.utils

import android.content.Context
import java.io.IOException


fun Context.loadFromAssets(assetName: String): String? {
    val json: String?

    try {
        val `is` = assets.open(assetName)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        json = String(buffer)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }

    return json
}
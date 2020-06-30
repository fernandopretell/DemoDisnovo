@file:JvmName("UrlUtil")

package com.limapps.base.utils

import okhttp3.HttpUrl
import java.util.HashMap
import java.util.Locale
import java.util.regex.Pattern

const val PARAM_TOKEN = "token"
const val PARAM_LANGUAGE = "language"
const val RAPPI_SCHEME_VALIDATOR = "(https?:\\/\\/(.+?\\.)?rappi\\.com(\\/[A-Za-z0-9\\-\\._~:\\/\\?#\\[\\]@!$&'\\(\\)\\*\\+,;\\=]*)?)"

fun appendQueryParameterToUrl(url: String, key: String, value: String): String {
    return StringBuilder(url).append("&").append(key).append("=").append(value).toString()
}

fun appendQueryParameterToUrl(url: String, params: HashMap<String, String>): String {
    val builder = StringBuilder(url)
    for ((key, value) in params) {
        builder.append("&").append(key).append("=").append(value)
    }
    return builder.toString()
}

fun updateLanguage(url: String): String {
    val newUrl: String? = if (isRappiScheme(url)) {
        HttpUrl.parse(url)?.newBuilder()
                ?.addQueryParameter(PARAM_LANGUAGE, Locale.getDefault().language)
                ?.build()?.url()?.toString()
    } else {
        url
    }
    return newUrl.orEmpty()
}

fun isRappiScheme(url: String): Boolean {
    val pattern: Pattern = Pattern.compile(RAPPI_SCHEME_VALIDATOR)
    return pattern.matcher(url).matches()
}

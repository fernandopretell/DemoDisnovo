package com.limapps.base.handlers

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.limapps.base.utils.CountryUtil

private const val NEARBY_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
private const val PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json"
private const val LOCATION_QUERY = "location"
private const val RADIUS_QUERY = "radius"
private const val RADIUS_PARAM = "50000"
private const val KEY_WORD_QUERY = "keyword"
private const val KEY_INPUT_QUERY = "input"
private const val LANGUAGE_QUERY = "language"
private const val LANGUAGE_PARAM_PT_BR = "pt-BR"
private const val LANGUAGE_PARAM_ES = "es"

const val KEY_QUERY = "key"

private val apiKey: String by lazy { MapsHandler.apiKey }

private fun getBaseLocationBuilder(baseUrl: String, location: String): Uri.Builder {
    return Uri.parse(baseUrl)
            .buildUpon()
            .appendQueryParameter(LOCATION_QUERY, location.trim { it <= ' ' })
            .appendQueryParameter(LANGUAGE_QUERY, if (CountryUtil.isBrazil()) LANGUAGE_PARAM_PT_BR else LANGUAGE_PARAM_ES)
            .appendQueryParameter(KEY_QUERY, apiKey)
}

fun buildNearbyUrl(query: String, location: LatLng): String {
    return getBaseLocationBuilder(NEARBY_BASE_URL, buildStringLocation(location))
            .appendQueryParameter(RADIUS_QUERY, RADIUS_PARAM)
            .appendQueryParameter(KEY_WORD_QUERY, query)
            .build().toString().replace("%2C", ",")
}

fun buildStringLocation(location: LatLng): String =
        "${formatCoordinates(location.latitude.toString())},${formatCoordinates(location.longitude.toString())}"

fun formatCoordinates(longitude: String) = longitude.replace(",", ".")

fun buildAutocompletePlacesUrl(query: String, location: String): String {
    return getBaseLocationBuilder(PLACES_BASE_URL, location)
            .appendQueryParameter(KEY_INPUT_QUERY, query)
            .build().toString().replace("%2C", ",")
}
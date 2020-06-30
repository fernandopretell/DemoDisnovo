package com.limapps.base.handlers

import android.net.Uri
import androidx.annotation.NonNull
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.limapps.base.models.GoogleDirections
import com.limapps.base.models.PlaceSorted
import com.limapps.base.others.Callback
import com.limapps.base.others.CallbackAdapter
import com.limapps.base.repositories.PlacesRepository
import io.reactivex.Single
import java.util.*

object MapsHandler {

    private const val PLACE_DETAILS_BASE_URL = "https://maps.googleapis.com/maps/api/place/details/json"
    private const val DIRECTIONS_BASE_URL = "https://maps.googleapis.com/maps/api/directions/json"
    private const val STATIC_BASE_URL = "https://maps.googleapis.com/maps/api/staticmap"

    private const val PLACE_ID_QUERY = "placeid"
    private const val DEFAULT_COORDINATE = 0.0
    private const val KEY_ORIGIN = "origin"
    private const val KEY_DESTINATION = "destination"
    private const val KEY_WAYPOINTS = "waypoints"
    private const val KEY_SENSOR = "sensor"
    private const val KEY_LAT_LNG = "latlng"
    private const val KEY_ALTERNATIVES = "alternatives"
    private const val KEY_MODE = "mode"
    private const val MODE_PARAM = "driving"

    private const val CENTER = "center"
    private const val ZOOM = "zoom"
    private const val FORMAT = "FORMAT"
    private const val MAP_TYPE = "maptype"
    private const val SIZE = "size"

    private const val JSON_KEY_PLACE_ID = "place_id"
    private const val JSON_KEY_RESULT = "result"
    private const val JSON_KEY_PREDICTIONS = "predictions"
    private const val JSON_KEY_GEOMETRY = "geometry"
    private const val JSON_KEY_LOCATION = "location"
    private const val JSON_KEY_LATITUDE = "lat"
    private const val JSON_KEY_LONGITUDE = "lng"
    private const val JSON_KEY_TYPES = "types"
    private const val JSON_KEY_STRUCTURED_FORMATTING = "structured_formatting"
    private const val JSON_KEY_MAIN_TEXT = "main_text"
    private const val JSON_KEY_SECONDARY_TEXT = "secondary_text"

    //    private val placesRepository by lazy { PlacesRepository() }
    lateinit var apiKey: String

    fun init(googleApiKey: String) {
        apiKey = googleApiKey
    }

    private fun getLocation(responseObject: JsonObject): LatLng {
        val locationObject = responseObject.get(JSON_KEY_GEOMETRY)?.asJsonObject?.get(JSON_KEY_LOCATION)?.asJsonObject
                ?: JsonObject()
        val latitude = locationObject.get(JSON_KEY_LATITUDE)?.asDouble ?: DEFAULT_COORDINATE
        val longitude = locationObject.get(JSON_KEY_LONGITUDE)?.asDouble ?: DEFAULT_COORDINATE
        return LatLng(latitude, longitude)
    }

    fun buildPlaceDetailsUrl(placeId: String): String {
        return Uri.parse(PLACE_DETAILS_BASE_URL)
                .buildUpon()
                .appendQueryParameter(KEY_QUERY, apiKey)
                .appendQueryParameter(PLACE_ID_QUERY, placeId)
                .build().toString().replace("%2C", ",")
    }

    private fun buildDirectionsUrl(origin: LatLng, destination: LatLng, waypoints: List<LatLng>? = null): String {

        val uriBuilder = Uri.parse(DIRECTIONS_BASE_URL)
                .buildUpon()
                .appendQueryParameter(KEY_QUERY, apiKey)
                .appendQueryParameter(KEY_ORIGIN, buildStringLocation(origin))
                .appendQueryParameter(KEY_DESTINATION, buildStringLocation(destination))
                .appendQueryParameter(KEY_SENSOR, false.toString())
                .appendQueryParameter(KEY_ALTERNATIVES, false.toString())
                .appendQueryParameter(KEY_MODE, MODE_PARAM)

        waypoints?.let {
            uriBuilder.appendQueryParameter(KEY_WAYPOINTS, buildWaypointsString(waypoints))
        }
        return uriBuilder
                .build().toString().replace("%2C", ",")
    }

    fun buildDirectionStaticMapUrl(latLng: LatLng, style: String): String {
        return Uri.parse(STATIC_BASE_URL)
                .buildUpon()
                .appendQueryParameter(CENTER, buildStringLocation(latLng))
                .appendQueryParameter(ZOOM, "16")
                .appendQueryParameter(FORMAT, "png")
                .appendQueryParameter(MAP_TYPE, "roadmap")
                .appendQueryParameter(SIZE, "100x100")
                .appendQueryParameter(KEY_QUERY, apiKey)
                .build().toString() + style
    }

    private fun buildWaypointsString(waypoints: List<LatLng>): String {
        val stringBuilder = StringBuilder()

        waypoints.forEachIndexed { index, coordinate ->
            stringBuilder.append(buildStringLocation(coordinate))

            if (index != waypoints.lastIndex) {
                stringBuilder.append("|")
            }
        }
        return stringBuilder.toString()
    }

    fun getAddresses(query: String, location: LatLng, placesRepository: PlacesRepository): Single<List<PlaceSorted>> {
        val stringLocation = buildStringLocation(location)
        return placesRepository.getAutocompletePlacesObservable(buildAutocompletePlacesUrl(query, stringLocation))
                .map {
                    manageAutocompletePlacesResponse(it)
                }
    }

    @NonNull
    private fun manageAutocompletePlacesResponse(resolved: JsonObject): List<PlaceSorted> {
        val response = resolved.getAsJsonArray(JSON_KEY_PREDICTIONS) ?: JsonArray()
        return response.mapNotNull { jsonElement -> getPlaceSortedFromAutocompleteResponse(jsonElement) }.sorted()
    }

    private fun getPlaceSortedFromAutocompleteResponse(jsonElement: JsonElement): PlaceSorted {
        val responseObject = jsonElement.asJsonObject ?: JsonObject()
        val placeId = responseObject.get(JSON_KEY_PLACE_ID)?.asString ?: ""
        val types = getTypes(responseObject)
        val structuredFormatting = responseObject.get(JSON_KEY_STRUCTURED_FORMATTING)?.asJsonObject
                ?: JsonObject()
        return PlaceSorted(placeId, Float.MAX_VALUE,
                structuredFormatting.get(JSON_KEY_MAIN_TEXT)?.asString
                        ?: "", structuredFormatting.get(JSON_KEY_SECONDARY_TEXT)?.asString
                ?: "", null, false, types)
    }

    private fun getTypes(responseObject: JsonObject): List<String> {
        val types = ArrayList<String>()
        responseObject.get(JSON_KEY_TYPES).asJsonArray?.mapTo(types) { it.asString }
        return types
    }

    private fun getPlaceLocation(placeId: String, placesRepository: PlacesRepository, callback: CallbackAdapter<LatLng>) {
        placesRepository.getPlaceDetails(buildPlaceDetailsUrl(placeId)).subscribe({
            callback.onResolve(getPlaceLocationFromDetails(it))
        }, {
            callback.onReject(it)
            callback.onFinish()
        })
    }

    private fun getPlaceLocationFromDetails(resolved: JsonObject): LatLng {
        val response = resolved.getAsJsonObject(JSON_KEY_RESULT) ?: JsonObject()
        return getLocation(response)
    }

    fun getPlaceLocation(placeSelected: PlaceSorted, placesRepository: PlacesRepository, callback: Callback<PlaceSorted>) {
        if (placeSelected.id.isNullOrBlank().not()) {
            placeSelected.id?.let {
                getPlaceLocation(it, placesRepository, object : CallbackAdapter<LatLng>() {
                    override fun onResolve(location: LatLng) {
                        placeSelected.location = location
                        callback.onResolve(placeSelected)
                    }

                    override fun onReject(error: Throwable?) {
                        error?.printStackTrace()
                        callback.onReject(error)
                    }
                })
            }
        } else {
            callback.onReject(PlaceWithoutLocation())
        }
    }

    fun getRoute(origin: LatLng,
                 destination: LatLng,
                 waypoints: List<LatLng>? = null,
                 placesRepository: PlacesRepository): Single<GoogleDirections.Route> {
        return placesRepository.getRoute(buildDirectionsUrl(origin, destination, waypoints)).map { getDirectionsResult(it) }
    }

    private fun getDirectionsResult(result: JsonObject?): GoogleDirections.Route {
        return result?.let {
            GoogleDirections.Route.fromJsonObject(it.getAsJsonArray("routes")?.firstOrNull()?.asJsonObject)
        } ?: kotlin.run { GoogleDirections.Route() }
    }
}

class PlaceWithoutLocation : java.lang.Exception()
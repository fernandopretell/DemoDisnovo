package com.limapps.base.repositories

import android.location.Location
import androidx.annotation.NonNull
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.limapps.base.di.qualifiers.SwitchServices
import com.limapps.base.handlers.buildNearbyUrl
import com.limapps.base.managers.UrlManager
import com.limapps.base.models.LupapAddress
import com.limapps.base.models.PlaceSorted
import com.limapps.base.rest.ServiceGenerator
import com.limapps.base.rest.retrofit.services.PlacesService
import com.limapps.common.applySchedulers
import com.limapps.common.biLet
import com.limapps.common.getDoubleOrDefault
import com.limapps.common.getJsonArrayOrDefault
import com.limapps.common.getJsonObjectOrDefault
import io.reactivex.Single
import java.util.ArrayList
import javax.inject.Inject

class PlacesRepository @Inject constructor(@SwitchServices val placesService: PlacesService) : BaseRepository() {

    constructor() : this(ServiceGenerator.createService(PlacesService::class.java))

    fun getNearByPlaces(url: String, location: LatLng): Single<List<PlaceSorted>> {
        return placesService.getNearBy(buildNearbyUrl(url, location))
                .map { jsonObject -> manageNearbyPlacesResponse(jsonObject, location) }
                .onErrorReturn { emptyList() }.applySchedulers()
    }

    @NonNull
    private fun manageNearbyPlacesResponse(resolved: JsonObject, location: LatLng): List<PlaceSorted> {
        val response = resolved.getAsJsonArray(JSON_KEY_RESULTS) ?: JsonArray()
        return response.mapNotNull { jsonElement -> getPlaceSortedFromNearbyResponse(jsonElement, location) }.sorted()
    }

    private fun getPlaceSortedFromNearbyResponse(jsonElement: JsonElement, location: LatLng): PlaceSorted? {
        val responseObject = jsonElement.asJsonObject ?: JsonObject()
        val id = responseObject.get(JSON_KEY_PLACE_ID)?.asString ?: ""
        val placeLocation = getLocation(responseObject)
        val isOpen = !responseObject.has(JSON_KEY_OPENING_HOURS)
                || responseObject.get(JSON_KEY_OPENING_HOURS)?.asJsonObject?.get(JSON_KEY_OPEN_NOW)?.asBoolean ?: true
        val types = getTypes(responseObject)
        return PlaceSorted(id, getDistanceBetweenLocationsInMeters(location, placeLocation),
                responseObject.get(JSON_KEY_NAME).asString, responseObject.get(JSON_KEY_VICINITY).asString,
                placeLocation, isOpen, types)
    }

    private fun getDistanceBetweenLocationsInMeters(locationOne: LatLng?, locationTwo: LatLng?): Float {
        var result = -1f
        (locationOne to locationTwo).biLet { locOne, locTwo ->
            val results = FloatArray(1)
            Location.distanceBetween(locOne.latitude, locOne.longitude, locTwo.latitude, locTwo.longitude, results)
            result = results.first()
        }
        return result
    }

    private fun getLocation(responseObject: JsonObject): LatLng {
        val locationObject = responseObject.get(JSON_KEY_GEOMETRY)?.asJsonObject?.get(JSON_KEY_LOCATION)?.asJsonObject
                ?: JsonObject()
        val latitude = locationObject.get(JSON_KEY_LATITUDE)?.asDouble ?: DEFAULT_COORDINATE
        val longitude = locationObject.get(JSON_KEY_LONGITUDE)?.asDouble ?: DEFAULT_COORDINATE
        return LatLng(latitude, longitude)
    }

    private fun getTypes(responseObject: JsonObject): List<String> {
        val types = ArrayList<String>()
        responseObject.get(JSON_KEY_TYPES).asJsonArray?.mapTo(types) { it.asString }
        return types
    }

    fun getAutocompletePlacesObservable(url: String): Single<JsonObject> {
        return placesService.getAutocompletePlaces(url).applySchedulers()
    }

    fun getPlaceDetails(url: String): Single<JsonObject> {
        return placesService.getPlaceDetails(url).applySchedulers()
    }

    fun getRoute(url: String): Single<JsonObject> {
        return placesService.getRoute(url).applySchedulers()
    }

    fun findLupapAddress(city: String, countryCode: String, address: String): Single<LupapAddress> {
        return placesService.findLupapAddress(city, countryCode, address)
                .map { jsonObject -> Gson().fromJson(jsonObject.get(LupapAddress.RESPONSE), LupapAddress::class.java) }
    }

    fun getLocationByAddress(address: String, lat: Double, lng: Double): Single<LatLng> {
        val location = "$lat,$lng"
        return this.placesService.getLocationByAddress(UrlManager.googleLocation, UrlManager.premiumMapsApiKey, address, location)
                .map { mapLocation(it) }
    }

    private fun mapLocation(jsonObject: JsonObject): LatLng {

        val location = jsonObject.getJsonArrayOrDefault("results")
                ?.firstOrNull()?.asJsonObject
                .getJsonObjectOrDefault("geometry")
                ?.getJsonObjectOrDefault("location")

        val latitude = location?.getDoubleOrDefault("lat")

        val longitude = location?.getDoubleOrDefault("lng")

        return if (latitude == null || longitude == null) {
            LatLng(0.0, 0.0)
        } else {
            LatLng(latitude, longitude)
        }
    }

    companion object {
        private const val JSON_KEY_PLACE_ID = "place_id"
        private const val JSON_KEY_RESULTS = "results"
        private const val JSON_KEY_GEOMETRY = "geometry"
        private const val JSON_KEY_LOCATION = "location"
        private const val JSON_KEY_LATITUDE = "lat"
        private const val JSON_KEY_LONGITUDE = "lng"
        private const val JSON_KEY_NAME = "name"
        private const val JSON_KEY_VICINITY = "vicinity"
        private const val JSON_KEY_OPENING_HOURS = "opening_hours"
        private const val JSON_KEY_OPEN_NOW = "open_now"
        private const val JSON_KEY_TYPES = "types"
        private const val DEFAULT_COORDINATE = 0.0
    }
}
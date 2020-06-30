package com.limapps.base.maps.controllers

import com.limapps.base.location.Location
import com.limapps.base.maps.models.MapsPlace
import com.limapps.base.maps.models.SinglePlace
import io.reactivex.Single

interface PlaceController {

    fun getPlaceCoordinatesById(placeId: String, tokenId: String): Single<Location>

    fun getPlaceDataByCoordinates(location: Location, tokenId: String): Single<SinglePlace>

    fun validatePlaceIsOpen(placeId: String, tokenId: String) : Single<Boolean>

    //fun validateWhimStoreIsOpen(placeId: String, tokenId: String) : Single<Pair<Boolean, Boolean?>>

    fun getPlacesAutocomplete(query: String, location: Location, autocompleteByGoogle: Boolean = true): Single<List<MapsPlace>>

    fun getPlacesAutocomplete(query: String, radius: Int, location1: Location, tokenId: String, components: String?, strictQuery: Boolean): Single<List<MapsPlace>>
}
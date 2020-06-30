package com.limapps.base.maps.controllers

import com.limapps.base.location.Location
import com.squareup.picasso.RequestCreator

interface StaticMapsController {

    fun loadCenteredMapUrl(
            style: String,
            locations: List<Location>,
            hasMarkers: Boolean,
            width: Int,
            height: Int): RequestCreator

}
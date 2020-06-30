package com.limapps.base.maps.controllers

import com.limapps.base.location.Location
import io.reactivex.Single

interface DirectionsController {

    fun getRoute(origin: Location,
                 destination: Location,
                 wayPoints: List<Location>? = null): Single<List<Location>>
}
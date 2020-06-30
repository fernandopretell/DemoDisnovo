package com.limapps.base.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.limapps.common.biLet

fun getDistanceBetweenLocationsInMeters(locationOne: LatLng?, locationTwo: LatLng?): Float {
    var result = -1f

    (locationOne to locationTwo).biLet { one, two ->
        val results = FloatArray(1)
        Location.distanceBetween(one.latitude, one.longitude, two.latitude, two.longitude, results)
        result = results.first()
    }

    return result
}

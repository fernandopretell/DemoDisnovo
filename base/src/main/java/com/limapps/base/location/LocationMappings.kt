package com.limapps.base.location

import com.google.android.gms.maps.model.LatLng

fun Location.toLatLng(): LatLng {
    return LatLng(latitude, longitude)
}

fun LatLng.toLocation(): Location {
    return Location(latitude, longitude)
}
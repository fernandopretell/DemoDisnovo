package com.limapps.base.maps.models

import com.google.android.gms.maps.model.LatLng

data class MapsPlace(val id: String,
                     val text: String = "",
                     val source: String = com.limapps.base.utils.GOOGLE,
                     val description: String = "",
                     val types: List<String> = listOf(),
                     val location: LatLng? = null)
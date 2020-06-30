package com.limapps.base.models.store

import com.google.gson.annotations.SerializedName

//@Parcelize
data class StoreMB(
        @SerializedName("name_store") val name_store: String? = "",
        @SerializedName("distance") val distance: Float? = null
        //@SerializedName("geoHash") val geoHash: String? = null
)
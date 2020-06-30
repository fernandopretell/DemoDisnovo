package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class CountryModel(
        @SerializedName("name") val countryName: String = "",
        @SerializedName("code") val countryCode: String = "",
        @SerializedName("code_iso_2") val codeIso: String = "",
        @SerializedName("flag") val flagUrl: String? = null,
        @SerializedName("lat") val latitude: Double = 0.0,
        @SerializedName("lng") val longitude: Double = 0.0,
        @Transient val flagDrawable: Int = -1,
        @Transient var isSelected: Boolean = false
) {

    val cities: MutableList<City> = ArrayList()

    fun addCity(cities: List<City>?) {
        cities?.let {
            this.cities.addAll(cities)
        }
    }
}

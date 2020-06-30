package com.limapps.base.rest.services

import com.google.gson.JsonArray
import com.limapps.base.models.City
import com.limapps.base.models.CountryModel
import com.limapps.base.models.ServerConfig
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CountriesAndCitiesServices {

    @GET("api/location/resolve-country")
    fun resolveCountry(@Query("lat") latitude: String, @Query("lng") longitude: String): Single<ServerConfig>

    @GET("api/ms/country-data/")
    fun getDataByCountry(): Single<JsonArray>

    @GET("api/city-addresses")
    fun getCities(): Single<List<City>>

    @GET("api/ms/country-data/available-countries")
    fun getCountries(): Single<List<CountryModel>>
}
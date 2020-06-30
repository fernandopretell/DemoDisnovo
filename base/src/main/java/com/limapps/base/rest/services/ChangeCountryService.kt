package com.limapps.base.rest.services

import com.limapps.base.models.world.LocationChangeCountry
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ChangeCountryService {

    @GET("api/location/change-country-v2")
    fun locationChangeCountry(
            @Query("latOrigin") latOrigin: String,
            @Query("lngOrigin") lngOrigin: String,
            @Query("latDestination") latDestination: String,
            @Query("lngDestination") lngDestination: String
    ): Single<LocationChangeCountry>
}
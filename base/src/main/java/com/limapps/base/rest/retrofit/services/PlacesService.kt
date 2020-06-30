package com.limapps.base.rest.retrofit.services

import com.google.gson.JsonObject

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PlacesService {

    @GET
    fun getNearBy(@Url url: String): Single<JsonObject>

    @GET
    fun getAutocompletePlaces(@Url url: String): Single<JsonObject>

    @GET
    fun getPlaceDetails(@Url url: String): Single<JsonObject>

    @GET
    fun getRoute(@Url url: String): Single<JsonObject>

    @GET("api/web/city/address")
    fun findLupapAddress(@Query("city") city: String,
                         @Query("country") country: String,
                         @Query("address") address: String): Single<JsonObject>

    @GET
    fun getLocationByAddress(@Url url: String,
                             @Query("key") key: String,
                             @Query("keyword") address: String,
                             @Query("location") location: String): Single<JsonObject>


}

package com.limapps.base.rest.services

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HasCoverageService {

    @GET("api/base-crack/has-coverage")
    fun hasCoverage(@Query("lat") latitude: String, @Query("lng") longitude: String): Single<JsonObject>
}
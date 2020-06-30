package com.limapps.base.rest.services

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.limapps.base.models.ServerTime
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface InformationService {

    @GET("api/ms/country-data/application-version/android/check/{version}/1")
    fun checkVersion(@Path("version") version: String): Single<JsonObject>

    @GET("api/status/server-time")
    fun getServerTime(@Query("lat") lat: String?, @Query("lng") lng: String?): Observable<ServerTime>

    @GET("api/countries")
    fun getCountryCodes(): Single<JsonArray>
}

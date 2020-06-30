package com.limapps.base.repositories

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.limapps.base.di.qualifiers.BasePath
import com.limapps.base.di.qualifiers.MicroService
import com.limapps.common.applySchedulers
import com.limapps.base.models.ServerTime
import com.limapps.base.rest.services.InformationService
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class AppRepository @Inject constructor(@BasePath val service: InformationService,
                                        @MicroService val microservice: InformationService) {

    fun checkVersion(version: String): Single<JsonObject> {
        return microservice.checkVersion(version).applySchedulers()
    }

    fun getServerTime(latitude: String?, longitude: String?): Observable<ServerTime> {
        return service.getServerTime(latitude, longitude).applySchedulers()
    }

    fun getCountryCodes(): Single<JsonArray> = service.getCountryCodes().applySchedulers()
}
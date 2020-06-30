package com.limapps.base.repositories

import com.google.gson.JsonArray
import com.limapps.base.models.Address
import com.limapps.base.models.City
import com.limapps.base.models.CountryModel
import com.limapps.base.models.ServerConfig
import com.limapps.base.models.world.LocationChangeCountry
import io.reactivex.Single

interface CountriesAndCitiesRepository {

    fun getCities(): Single<List<City>>

    fun getCountries(): Single<List<CountryModel>>

    fun getDataByCountry(): Single<JsonArray>

    fun resolveCountry(latitude: String, longitude: String): Single<ServerConfig>

    fun locationChangeCountry(currentAddress: Address, newAddress: Address): Single<LocationChangeCountry>

    fun hasCoverage(latitude: Double, longitude: Double, endPoint: String?): Single<Boolean>
}
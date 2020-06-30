package com.limapps.base.handlers

import com.google.gson.JsonArray
import com.limapps.base.models.City
import com.limapps.base.models.CountryModel
import com.limapps.base.models.ServerConfig
import io.reactivex.Observable
import io.reactivex.Single

interface CountriesController {


    fun selectCity(city: City)

    fun selectCountry(countryModel: CountryModel)

    fun citySelected(): Observable<City>

    fun selectOtherCountry(): Observable<Boolean>

    fun changeCountry()

    fun getCurrentCountry(): CountryModel?

    fun countrySelected(): Observable<CountryModel>

    fun getCurrentCities(): Single<List<City>>

    fun getCountries(): Single<List<CountryModel>>

    fun getCities(): Single<List<City>>

    fun resolveCountry(latitude: String, longitude: String): Single<ServerConfig>

    fun getDataByCountry(): Single<JsonArray>
}
package com.limapps.base.stores

import com.limapps.base.models.store.StoreTypeModelV2
import io.reactivex.Observable

interface StoresController {
    fun getStoreForStoreType(storeType: String): Observable<StoreTypeModelV2>
    fun getStoreTypeModelForStoreType(vararg storeType: String): StoreTypeModelV2?
    fun observableStores(): Observable<List<StoreTypeModelV2>>
    fun getCurrentStores(forceUpdate: Boolean = false): Observable<List<StoreTypeModelV2>>
    fun populateStores(stores: List<StoreTypeModelV2>)
    fun getStoresForStoreTypes(storeTypes: List<String>): Observable<List<StoreTypeModelV2>>
    fun loadStoreController()
}


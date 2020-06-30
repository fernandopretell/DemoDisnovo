package com.limapps.base.rest.retrofit.services

import com.limapps.base.models.StoresDetailRequest
import com.limapps.base.models.store.StoreInformation
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface StoresService {

    @POST("api/base-crack/available/delivery-information")
    fun getStoreInformation(@Body stores: StoresDetailRequest): Single<List<StoreInformation>>

    companion object {
        const val DEVICE = "device"
        const val LATITUDE = "lat"
        const val LONGITUDE = "lng"
        const val STORE_ID = "store_id"
        const val STORE_TYPE = "type"
        const val PRODUCT_COUNT = "products_count"
    }
}

package com.limapps.base.rest.retrofit.services

import com.limapps.base.support.history.OrderHistory
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderHistoryService {

    @GET("api/ms/order-history/history-user")
    fun getOrderHistory(@Query("page") page: Int): Single<OrderHistory>

}

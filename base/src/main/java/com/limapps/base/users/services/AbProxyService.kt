package com.limapps.base.users.services

import com.limapps.base.models.AbProxyRequest
import com.limapps.base.models.AbProxyResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AbProxyService {
    @POST("core-ab-proxy/evaluate/multiple")
    fun getAbProxy(@Body body: AbProxyRequest): Single<ArrayList<AbProxyResponse>>
}
package com.limapps.demodisnovo.source

import com.limapps.demodisnovo.models.ObjectPut
import com.limapps.demodisnovo.util.Constants
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by fernandopretell.
 */
interface WebServiceData {

    @Headers("Content-Type: application/json","token: ${Constants.TOKEN}")
    @GET("form/")
    fun obtenerDataRemota(): Call<ResponseApi>

    @Headers("Content-Type: application/json","token: ${Constants.TOKEN}")
    @POST("form")
    fun enviarDataRemota(@Body objectPut: ObjectPut): Call<ResponseApiPost>
}
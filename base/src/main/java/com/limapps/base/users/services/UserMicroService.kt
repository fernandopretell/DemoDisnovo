package com.limapps.base.users.services

import com.google.gson.JsonObject
import com.limapps.base.models.UserInfoModel
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


@Deprecated(message = "Use MicroService at user module")
interface UserMicroService {

    @Deprecated("old model")
    @GET("ms/application-user/auth")
    fun getInformation(): Single<UserInfoModel>

    @PUT("api/rocket/user/account/phone/{method}/application_user")
    fun updatePhoneNumber(@Path("method") method: String, @Body body: RequestBody): Single<JsonObject>

}

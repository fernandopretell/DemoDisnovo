package com.limapps.base.users.services

import com.limapps.base.models.UserInfoModel
import com.limapps.base.users.StateCreditRappi
import com.limapps.base.users.UserRappiCredit
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface UserService {

    @GET("api/credit/user")
    fun getUserRappiCredit(): Observable<UserRappiCredit>

    @PUT("api/application-users")
    fun update(@Body body: UserInfoModel): Observable<UserInfoModel>

    @PUT("api/credit/user/state")
    fun setStateRappiCredit(@Body body: StateCreditRappi): Single<JSONObject>

    @Multipart
    @POST("api/application-users/profile-pic")
    fun updatePicture(@Part requestBody: MultipartBody.Part, @Part body: MultipartBody.Part): Observable<String>

}

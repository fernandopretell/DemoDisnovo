package com.limapps.base.handlers

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.limapps.base.models.AuthData
import com.limapps.base.models.RequestCodeResponse
import com.limapps.base.models.ResendServiceRequest
import com.limapps.base.models.ResendServiceResponse
import com.limapps.base.models.SocialNetwork
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

const val DUPLICATE_DEVICE_ID = "users.duplicated_device_id"

interface AccountController {

    fun getFirstName(): String

    fun loginSuccess(): Observable<AuthData>

    fun loginWithFacebook(activity: androidx.fragment.app.Fragment): Completable

    fun authLogin(firstName: String, lastName: String, email: String, password: String,
                  identification: String, identificationType: Int): Completable

    fun verifyEmail(email: String): Observable<Boolean>

    fun loginByEmail(email: String): Completable

    fun onFacebookLoginResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun updatePhoneNumber(method: String, phone: String, countryCode: String,
                          socialId: String, accessToken: String): Single<Boolean>

    fun logoutOtherSessions(): Completable

    fun logout(): Completable

    fun requestCode(socialNetwork: SocialNetwork, dialCode: String?, phoneNumber: String?, via: String): Single<RequestCodeResponse>

    fun sendCode(socialNetwork: SocialNetwork, code: String, dialCode: String, phoneNumber: String, uuid: String, justVerify: Boolean): Completable

    fun recoveryPassword(email: String): Completable

    fun isRegistryInProgress(): Boolean

    fun loginWithGoogle(account: GoogleSignInAccount): Completable

    fun loginRecover(): Completable

    fun updateRegistryObject(email: String, name: String)

    fun setEmailLoginInfo(email: String)

    fun resendCode(resendServiceRequest: ResendServiceRequest, channel: String): Single<ResendServiceResponse>

    fun getEmail(): String
}
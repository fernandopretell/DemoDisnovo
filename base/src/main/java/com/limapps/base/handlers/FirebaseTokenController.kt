package com.limapps.base.handlers

import com.limapps.base.models.FirebaseToken
import io.reactivex.Single

interface FirebaseTokenController {

    fun getFirebaseToken(): Single<FirebaseToken>
}
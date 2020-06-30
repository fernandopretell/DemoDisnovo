package com.limapps.base.handlers

import android.content.Intent
import io.reactivex.Single


interface PhoneVerificationController {

    fun extractPhoneAndDialPair(data: Intent): Single<Boolean>
}
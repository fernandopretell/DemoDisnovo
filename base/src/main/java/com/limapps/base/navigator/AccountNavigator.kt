package com.limapps.base.navigator

import android.content.Intent

const val PHONE_VERIFICATION = 0x001
const val GOOGLE_SIGN_IN = 0x002
const val TERMS_AND_CONDITIONS = 0x003

interface AccountNavigator {
    fun getPhoneVerificationIntent(method:String? = null, justVerify: Boolean = false): Intent

    fun getCurrentVerificationMethod(): String
}
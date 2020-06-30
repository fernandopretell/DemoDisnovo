package com.limapps.base.checkout.models

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
class WebViewCheckoutToken(val token: String,
                           val description: String,
                           val storeType: String,
                           val total: Double) : Parcelable {


    companion object {
        fun fromJson(json: String): WebViewCheckoutToken {
            return Gson().fromJson(json, WebViewCheckoutToken::class.java)
        }
    }
}

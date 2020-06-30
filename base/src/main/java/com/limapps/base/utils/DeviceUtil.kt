package com.limapps.base.utils

import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.limapps.common.applySchedulersOnIo
import com.limapps.common.tryOrDefault
import io.reactivex.Single

fun getAdvertisingId(context: Context): Single<String> {
    return Single.fromCallable {
        tryOrDefault({
            AdvertisingIdClient.getAdvertisingIdInfo(context).id
        }, StringUtils.EMPTY_STRING)
    }.applySchedulersOnIo()
}
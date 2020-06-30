package com.limapps.base.interfaces

import android.os.Bundle
import com.limapps.base.utils.Optional
import io.reactivex.Observable

interface RappiBroadcast {

    fun sendBroadcastWithAction(action: String, bundle: Bundle)

    fun registerBroadcastForAction(action: String): Observable<Optional<Map<String,String>>>
}
package com.limapps.base

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.limapps.base.interfaces.RappiBroadcast
import com.limapps.base.utils.None
import com.limapps.base.utils.Optional
import com.limapps.base.utils.toOptional
import com.limapps.baseui.biLet
import io.reactivex.Observable
import javax.inject.Inject

class RappiBroadcastImpl @Inject constructor(private val application: Application) : RappiBroadcast {

    private val localBroadcastManager by lazy { LocalBroadcastManager.getInstance(application) }

    override fun sendBroadcastWithAction(action: String, bundle: Bundle) {
        val intent = Intent(action)
        intent.putExtras(bundle)
        localBroadcastManager.sendBroadcast(intent)
    }

    override fun registerBroadcastForAction(action: String): Observable<Optional<Map<String, String>>> {
        lateinit var broadcastReceiver: BroadcastReceiver
        return Observable.create<Optional<Map<String, String>>> { emitter ->
            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {

                    val map = intent?.extras?.keySet()?.map { key ->
                        Pair(key, intent.getStringExtra(key).orEmpty())
                    }.orEmpty().toMap()

                    emitter.onNext(map.toOptional())
                }
            }

            localBroadcastManager.registerReceiver(broadcastReceiver, IntentFilter(action))
        }.doOnDispose {
            localBroadcastManager.unregisterReceiver(broadcastReceiver)
        }
    }
}
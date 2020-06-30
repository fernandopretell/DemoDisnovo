package com.limapps.base

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.threetenabp.AndroidThreeTen
import com.limapps.base.others.ResourcesProvider
import com.limapps.base.utils.LogUtil
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

fun initFirebasePersistence() {
    FirebaseDatabase.getInstance().setPersistenceEnabled(true)
}

fun initThreeTen(application: Application) {
    AndroidThreeTen.init(application)
}

fun initResourceProvider(application: Application) {
    ResourcesProvider.init(application)
}

fun initRxJavaErrorHandler() {
    RxJavaPlugins.setErrorHandler { error ->
        when (error) {
            is UndeliverableException -> {
                LogUtil.e("Error", "UndeliverableException: " + error.message)
            }
            else -> {
                throw error
            }
        }
    }
}

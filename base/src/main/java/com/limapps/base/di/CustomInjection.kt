package com.limapps.base.di

import android.app.Activity
import com.limapps.base.RappiApplication
import dagger.android.AndroidInjection

/*
fun Activity.inject(providerKey: String? = null) {
    if (providerKey == null) {
        AndroidInjection.inject(this)
    } else {
        (application as RappiApplication).component.componentProviders[providerKey]?.androidInjector()?.inject(this)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> Activity.getComponent(providerKey: String): T {
    return (application as RappiApplication).component.componentProviders[providerKey] as T
}*/

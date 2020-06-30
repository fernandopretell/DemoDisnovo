package com.limapps.base.rest.interceptors

import com.limapps.base.persistence.preferences.PreferencesManager

class MicroServicesUrlInterceptor : BaseUrlInterceptor {

    override fun getPath(): String {
        return PreferencesManager.baseMicroService().get()
    }
}
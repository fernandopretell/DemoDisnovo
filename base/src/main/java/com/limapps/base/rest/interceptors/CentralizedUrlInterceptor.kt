package com.limapps.base.rest.interceptors

import com.limapps.base.persistence.preferences.PreferencesManager

class CentralizedUrlInterceptor : BaseUrlInterceptor {

    override fun getPath(): String {
        return PreferencesManager.baseCentralizedService().get()
    }
}
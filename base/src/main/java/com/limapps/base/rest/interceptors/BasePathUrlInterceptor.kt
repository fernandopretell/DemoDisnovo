package com.limapps.base.rest.interceptors

import com.limapps.base.persistence.preferences.PreferencesManager

class BasePathUrlInterceptor : BaseUrlInterceptor {

    override fun getPath(): String {
        return PreferencesManager.basePath().get()
    }
}
package com.limapps.base.support.controllers

import com.google.gson.Gson
import com.limapps.base.di.qualifiers.MicroService
import com.limapps.base.models.AbProxyRequest
import com.limapps.base.models.Attributes
import com.limapps.base.others.RappiData
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.support.model.HelpCenterConfig
import com.limapps.base.users.services.AbProxyService
import com.limapps.common.applySchedulers
import io.reactivex.Single
import javax.inject.Inject

class HelpCenterSplitControllerImpl @Inject constructor(
        @MicroService private val abProxyService: AbProxyService,
        private val preferencesManager: PreferencesManager,
        private val gson: Gson
) : HelpCenterSplitController {

    override fun getConfig(): Single<HelpCenterConfig> {
        val request = AbProxyRequest(
                features = arrayListOf(SUPPORT_HELP_CENTER_CONFIG_KEY),
                attributes = Attributes(
                        country = preferencesManager.countryCodeData().get(),
                        appVersion = RappiData.appVersion
                ))
        return abProxyService.getAbProxy(request)
                .applySchedulers()
                .onErrorResumeNext{ Single.just(arrayListOf()) }
                .map {
                    it.firstOrNull()?.let { item ->
                        gson.fromJson(gson.toJson(item.config), HelpCenterConfig::class.java)
                    } ?: HelpCenterConfig()
                }
    }

    companion object {
       const val SUPPORT_HELP_CENTER_CONFIG_KEY = "support_help_center_v2"
    }
}
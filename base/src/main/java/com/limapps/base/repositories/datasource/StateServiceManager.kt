package com.limapps.base.repositories.datasource

import com.google.gson.JsonArray
import com.limapps.base.R
import com.limapps.base.managers.FileManager
import com.limapps.base.models.CountryModel
import com.limapps.base.models.ServerConfig
import com.limapps.base.models.world.ChangeLocationResponse
import com.limapps.base.others.IResourceProvider
import com.limapps.base.others.ResourcesProvider
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.rest.RetrofitBuilderGenerator
import com.limapps.base.utils.BaseDataProvider
import javax.inject.Inject

class StateServiceManager @Inject constructor(private val dataProvider: BaseDataProvider,
                                              private val resourceProvider: IResourceProvider) {

    fun resetRetrofitInstance() {
        RetrofitBuilderGenerator.invalidateBuilder()
        PreferencesManager.basePath().set(resourceProvider.getString(R.string.url_grability))
    }

    fun setServerInformation(serverConfig: ServerConfig) {
        val urlRemoteAssets = serverConfig.server + ResourcesProvider.getString(R.string.url_assets)
        PreferencesManager.apply {
            baseMicroService().set(serverConfig.services)
            baseCentralizedService().set(serverConfig.serverCentralized)
            basePath().set(serverConfig.server)
            imagesPath().set(serverConfig.images)
            imagesS3Path().set(serverConfig.imagesS3)
            countryCodeData().set(serverConfig.code)
            phoneCode().set(serverConfig.phonePrefix?.replace("+", "").orEmpty())
            supportUrl().set(serverConfig.support)
        }

        RetrofitBuilderGenerator.invalidateBuilder()

        FileManager.apply {
            val urlImages = serverConfig.imagesS3
            setUrlRemoteImages(urlImages ?: urlRemoteAssets)
        }
    }

    fun setServerInformation(serverConfig: Pair<ServerConfig, ChangeLocationResponse>) {
        setServerInformation(serverConfig.first)
        if (serverConfig.second.hasValidCredentials())
            setToken(serverConfig.second.accessToken, serverConfig.second.refreshToken)
    }

    fun saveCountryData(countryModel: CountryModel, json: JsonArray) {
        dataProvider.saveData(json.toString())
        PreferencesManager.setAdditionalData(json.toString())
        PreferencesManager.latitudeData().set(countryModel.latitude.toFloat())
        PreferencesManager.longitudeData().set(countryModel.longitude.toFloat())
    }

    private fun setToken(token: String, refreshToken: String) {
        PreferencesManager.apply {
            accessToken().set(token)
            refreshToken().set(refreshToken)
        }
    }

}
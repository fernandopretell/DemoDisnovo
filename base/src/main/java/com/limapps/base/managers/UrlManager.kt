package com.limapps.base.managers

import com.limapps.base.R
import com.limapps.base.others.ResourcesProvider
import com.limapps.base.persistence.preferences.PreferencesManager

object UrlManager {

    private val preferencesManager: PreferencesManager = PreferencesManager

    val urlRemoteImages: String
        get() =
            getBasePathIfInvalid(preferencesManager.imagesS3Path().get(), ResourcesProvider.getString(R.string.url_assets))

    val premiumMapsApiKey: String
        get() = ResourcesProvider.getString(R.string.premium_api_key)

    val googleLocation: String
        get() = ResourcesProvider.getString(R.string.url_google_location)

    val urlCreditCardRappi: String
        get() = ResourcesProvider.getString(R.string.url_credit_card_rappi)

    private fun getBasePathIfInvalid(path: String?, additionalPath: String = ""): String {
        return if (path.isNullOrBlank()) {
            preferencesManager.basePath().get() + additionalPath
        } else {
            path
        }
    }

}

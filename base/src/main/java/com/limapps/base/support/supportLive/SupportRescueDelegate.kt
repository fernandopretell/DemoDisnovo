package com.limapps.base.support.supportLive

import android.content.Intent
import com.limapps.base.others.getNewWebViewIntent
import com.limapps.base.persistence.preferences.PreferencesManager
import javax.inject.Inject

class SupportRescueDelegate @Inject constructor(private val preferenceManager: PreferencesManager
                                                //private val countryDataProvider: RappiCountryDataProvider
) {

    //private val countryCode by lazy { countryDataProvider.getCountryCode().toLowerCase() }

    fun getWebViewIntent(orderId: String, storeType: String?): Intent {
        val baseUrl = preferenceManager.supportUrl().get()
        val accessToken = preferenceManager.accessToken().get()

        val finalUrl = "$baseUrl/#/screen/RESCUE_POST_ETA?token=$accessToken" +
                "&order_id=$orderId" +
                "&screen_id=RESCUE_POST_ETA" +
               // "&country_code=$countryCode" +
                "&store_type=${storeType.orEmpty()}"

        return getNewWebViewIntent(finalUrl)
    }

}
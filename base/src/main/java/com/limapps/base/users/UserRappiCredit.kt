package com.limapps.base.users

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.limapps.base.persistence.preferences.PreferencesManager

data class UserRappiCredit(@Expose
                           @SerializedName("rappi_credits")
                           val rappiCredits: Double = 0.0,

                           @Expose
                           @SerializedName("rappi_credit_active")
                           val rappiCreditActive: Boolean = false,


                           @Expose
                           @SerializedName("rappi_pay_enabled_to_user")
                           val isRappiPayEnabledToUser: Boolean = false)

/*fun UserRappiCredit.saveInUserSettings() {
    val preferencesManager = PreferencesManager
    preferencesManager.userProfile?.let {

        val userAsJson = Gson().toJson(it.copy(rappiCreditActive = rappiCreditActive,
                rappiCredits = rappiCredits))

        preferencesManager.setUserProfile(userAsJson)
    }

}*/

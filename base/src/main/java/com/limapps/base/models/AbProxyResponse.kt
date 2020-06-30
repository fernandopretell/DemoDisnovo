package com.limapps.base.models

import com.google.gson.annotations.SerializedName
import com.limapps.base.utils.StringUtils

const val ADDRESSES_SPLIT_NAME = "core_address_conf"
const val HOME_LOCATION_SCREEN_SPLIT_NAME = "core_address_location_screen"
const val CORE_UI_CONF_SPLIT_NAME = "core_ui_conf"
const val CORE_MENU_INVOICE = "core_menu_invoice"
const val HOME_GLOBAL_SEARCH_SPLIT_NAME = "core_home_new_ui"
const val ACF_CLOSE_TIME = "acf_close_time"
const val ACF_DEFAULT_CLOSE_TIME = "acf_default_close_time"
const val ADDRESS_NEW_UI_CONFIG_SPLIT_NAME = "core_address_ui"
const val CORE_PHONE_VERIFICATION_METHOD = "core_phone_verification_method"

data class AbProxyResponse(

        @SerializedName("config") val config: AbConfigResponse? = AbConfigResponse(),
        @SerializedName("feature") val feature: String? = "",
        @SerializedName("treatment") val treatment: String? = ""
)

data class AbConfigResponse(
        @SerializedName("active") val active: Boolean? = false,
        @SerializedName("title") val title: String? = "",
        @SerializedName("autocomplete") val autocomplete: Boolean? = true,
        @SerializedName("autocomplete_chars") val autocompleteChars: Int? = 0,
        @SerializedName("autocomplete_delay") val autocompleteDelay: Int? = 0,
        @SerializedName("type") val type: String? = com.limapps.base.utils.GOOGLE,
        @SerializedName("new_profile") val newProfile: Boolean = false,
        @SerializedName("new_navbar") val newMenu: Boolean = false,
        @SerializedName("show") val show: Boolean? = false,
        @SerializedName("tab_bar") val tabBarGlobalSearchButtom: SplitGlobalSearchButtomExperiment? = SplitGlobalSearchButtomExperiment(),
        @SerializedName("close_time") val closeTime: Int? = 0,
        @SerializedName("default_open_time") val defaultOpenTime: String? = StringUtils.EMPTY_STRING,
        @SerializedName("default_close_time") val defaultCloseTime: String? = StringUtils.EMPTY_STRING,
        @SerializedName("order_creation") val isOrderCreationAvailable: Boolean = false,
        @SerializedName("open_store") val isStoreOpen: Boolean = false,
        @SerializedName("new_modal") val newAddressModal: Boolean = false,
        @SerializedName("texts") val texts: ArrayList<String>? = arrayListOf(),
        @SerializedName("method") val phoneVerificationMethod: String? = null
)

data class LocationTreatmentResponse(private val abConfigResponse: AbConfigResponse? = AbConfigResponse()) {
    @SerializedName("active")
    val active: Boolean = abConfigResponse?.active ?: false
    @SerializedName("title")
    val title: String = abConfigResponse?.title ?: ""
}

data class AddressesAutocompleteTreatmentResponse(private val abConfigResponse: AbConfigResponse? = AbConfigResponse()) {
    @SerializedName("autocomplete")
    val autocomplete: Boolean = abConfigResponse?.autocomplete ?: true
    @SerializedName("autocomplete_chars")
    val autocompleteChars: Int = abConfigResponse?.autocompleteChars ?: 0
    @SerializedName("autocomplete_delay")
    val autocompleteDelay: Int = abConfigResponse?.autocompleteDelay ?: 0
    @SerializedName("type")
    val type: String = abConfigResponse?.type ?: com.limapps.base.utils.GOOGLE
}

data class DataBillingTreatment(private val abConfigResponse: AbConfigResponse? = AbConfigResponse()) {
    @SerializedName("show")
    val show: Boolean = abConfigResponse?.show ?: false
}

data class AddressListNewModalTreatmentResponse(private val abConfigResponse: AbConfigResponse? = AbConfigResponse()) {
    @SerializedName("new_modal")
    val newAddressModal: Boolean = abConfigResponse?.newAddressModal ?: false
}

data class PhoneVerificationMethodTreatmentResponse(private val abConfigResponse: AbConfigResponse? = AbConfigResponse()) {
    @SerializedName("method")
    val phoneVerificationMethod: String = abConfigResponse?.phoneVerificationMethod ?: com.limapps.base.utils.PHONE_MIXED
}

data class AbProxyRequest(
        @SerializedName("features")
        val features: ArrayList<String> = arrayListOf(),
        @SerializedName("with_user_statistics")
        val withUserStatistics: Boolean = false,
        @SerializedName("attributes")
        val attributes: Attributes
)

data class Attributes(

        @SerializedName("app-version") val appVersion: String,
        @SerializedName("user-agent") val userAgent: String = "android",
        @SerializedName("country") val country: String
)


data class CoreUiConfTreatmentModel(private val abConfigResponse: AbConfigResponse? = AbConfigResponse()) {
    @SerializedName("new_profile")
    val newProfile: Boolean = abConfigResponse?.newProfile ?: true
    @SerializedName("new_navbar")
    val newMenu: Boolean = abConfigResponse?.newMenu ?: true
}

data class AcftimeSplitTreatmentModel(private val abConfigResponse: AbConfigResponse? = AbConfigResponse()) {
    @SerializedName("close_time") val closeTime: Int = abConfigResponse?.closeTime ?: 0
}

data class AcfDefaultScheduleTreatmentModel(private val abConfigResponse: AbConfigResponse? = AbConfigResponse()) {
    @SerializedName("default_open_time")
    val defaultOpenTime: String = abConfigResponse?.defaultOpenTime ?: StringUtils.EMPTY_STRING
    @SerializedName("default_close_time")
    val defaultCloseTime: String = abConfigResponse?.defaultCloseTime ?: StringUtils.EMPTY_STRING
    @SerializedName("order_creation")
    val isOrderCreationAvailable: Boolean = abConfigResponse?.isOrderCreationAvailable ?: false
    @SerializedName("open_store")
    val isStoreOpen: Boolean = abConfigResponse?.isStoreOpen ?: false
}

data class SplitGlobalSearchButtomExperiment(
        @SerializedName("main_option") val mainOption: String = "",
        @SerializedName("new_icons") val newIcons: Boolean = false
)

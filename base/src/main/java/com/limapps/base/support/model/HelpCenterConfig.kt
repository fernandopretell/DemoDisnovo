package com.limapps.base.support.model

import com.google.gson.annotations.SerializedName

data class HelpCenterConfig(
        @SerializedName("enable_new_services") val enableNewServices: Boolean = false,
        @SerializedName("show_my_history_v2") val showMyHistoryV2: Boolean = false,
        @SerializedName("show_new_help_center") val showNewHelpCenter: Boolean = false,
        @SerializedName("show_new_help_center_pay") val showNewHelpCenterPay: Boolean = false,
        @SerializedName("show_search") val showSearch: Boolean = false
)
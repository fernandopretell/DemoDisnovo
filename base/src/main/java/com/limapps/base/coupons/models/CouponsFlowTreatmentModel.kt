package com.limapps.base.coupons.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class CouponsFlowTreatmentModel(
        @SerializedName("configuration_type")
        val configurationType: String = ConfigurationType.LIFO.type,
        @SerializedName("enable_basket_coupon_progress")
        val enableBasketCouponProgress: Boolean = false,
        @SerializedName("enable_coupon_tabs")
        val enableCouponTabs: Boolean = false,
        @SerializedName("enable_msg_apply_store")
        val enableMsgApplyStore: Boolean = false,
        @SerializedName("enable_new_flow")
        val enableNewFlow: Boolean = false,
        @SerializedName("enable_redeem_dialog")
        val enableRedeemDialog: Boolean = false,
        @SerializedName("tabs_type")
        val tabsType: List<String> = listOf()
)
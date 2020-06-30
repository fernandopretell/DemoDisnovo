package com.limapps.base.dynamicrate.v1

import com.google.gson.annotations.SerializedName
import com.limapps.base.utils.StringUtils

data class DynamicRate(@SerializedName("store_id")
                       val storeId: String? = null,
                       @SerializedName("charges")
                       val charges: List<Charge>? = null) {

    companion object {
        const val SHIPPING = "shipping"
    }

    val chargesValueOfShipping: Double
        get() {
            return charges?.firstOrNull { it.chargeType == SHIPPING }
                    ?.value
                    ?: 0.0
        }

    fun getChargesShippingId(): String {
        return charges?.firstOrNull { it.chargeType == SHIPPING }?.id ?: StringUtils.EMPTY_STRING
    }
}

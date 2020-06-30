package com.limapps.base.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ToppingCategory(
        @SerializedName("id") val id: Long,
        @SerializedName("description") val description: String,
        @SerializedName("topping_type_id") val type: String,
        @SerializedName("min_toppings_for_categories") val minToppings: Int = 0,
        @SerializedName("max_toppings_for_categories") val maxToppings: Int = 0,
        @SerializedName("index") val index: Int = 0,
        @SerializedName("presentation_type_id") val presentationType: Int = 0,
        @SerializedName(value = "topping", alternate = ["toppings"]) val toppings: List<Topping> = emptyList()
) : Parcelable {

    companion object {
        const val RADIO_BUTTON_GROUP = 1
        const val SWITCH_ON_OFF = 2
        const val COMMENT = 3

        const val INCLUSIVE = "inclusive"
        const val EXCLUSIVE = "exclusive"
    }
}

fun ToppingCategory.isMandatory(): Boolean = minToppings > 0

fun ToppingCategory.isValid(): Boolean {
    val toppingsSelected = toppings.filter { it.isChecked }.sumBy { it.units ?: 1 }
    return toppingsSelected in minToppings..maxToppings
}

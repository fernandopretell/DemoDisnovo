package com.limapps.base.models

import android.os.Bundle
import com.limapps.base.models.StepSelectorItemModel.Companion.UPDATE_SELECTED_TOPPING
import com.limapps.base.models.StepSelectorItemModel.Companion.UPDATE_TOPPINGS

data class StepSelectorItemModel(val id: Int, val toppings: List<Topping>, val selectedTopping: Topping?) {

    companion object {

        const val UPDATE_SELECTED_TOPPING = "UPDATE_SELECTED_TOPPING"
        const val UPDATE_TOPPINGS = "UPDATE_TOPPINGS"
    }
}

fun StepSelectorItemModel.createBundleDiff(newItem: StepSelectorItemModel): Bundle {
    return Bundle().apply {
        if (newItem.toppings != toppings) putParcelableArrayList(UPDATE_TOPPINGS, ArrayList(toppings))
        if (newItem.selectedTopping != selectedTopping) putParcelable(UPDATE_SELECTED_TOPPING, selectedTopping)
    }
}
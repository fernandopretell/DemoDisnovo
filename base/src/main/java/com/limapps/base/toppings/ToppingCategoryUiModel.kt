package com.limapps.base.toppings

import com.limapps.base.models.Topping

sealed class ToppingCategoryUiModel {
    class ToppingChange(val toppings: MutableList<Topping>, val isCombo: Boolean = false) : ToppingCategoryUiModel()
}
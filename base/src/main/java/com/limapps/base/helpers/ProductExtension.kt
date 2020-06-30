package com.limapps.base.helpers

import com.limapps.base.R
import com.limapps.base.models.BaseProduct
import com.limapps.base.others.PriceCalculatorFactory
import com.limapps.base.others.ResourcesProvider
import com.limapps.base.others.SaleTypeConstants.UNIT
import com.limapps.base.others.SaleTypeConstants.WEIGHT_BOTH
import com.limapps.base.others.SaleTypeConstants.WEIGHT_UNIT
import com.limapps.base.others.SaleTypeConstants.WEIGHT_WEIGHT
import com.limapps.base.others.calculators.PriceCalculator

val KG = 1000.0

fun BaseProduct.isWeightProduct(): Boolean = with(getSaleType()) {
    isNotEmpty() && (equals(WEIGHT_BOTH, true)
            || equals(WEIGHT_WEIGHT, ignoreCase = true))
}

fun BaseProduct.isWeightProductBasket(): Boolean =
        getSaleTypeBasket().equals(WEIGHT_WEIGHT, ignoreCase = true)

fun BaseProduct.isWeightBasketSaleType(): Boolean = ((isWeightProduct() || isWeightProductBasket())
        && WEIGHT_UNIT != getSaleTypeBasket()
        && UNIT != getSaleTypeBasket())

fun BaseProduct.getPriceCalculator(): PriceCalculator =
        PriceCalculatorFactory.getPriceCalculator(isWeightBasketSaleType())

fun BaseProduct.formatQuantity(): String = if (isWeightProductBasket())
    labelGrams()
else
    getQuantity().toString()

fun BaseProduct.formatQuantityWithBasketSaleType(): String = if (isUnit())
    getQuantity().toString()
else
    labelGrams()

fun BaseProduct.isUnit(): Boolean =
        getSaleTypeBasket() == UNIT || getSaleTypeBasket() == WEIGHT_UNIT

fun BaseProduct.labelGrams(): String {
    val grams = getGrams()
    return if (grams >= KG)
        ResourcesProvider.getString(R.string.copy_product_kilograms, grams / KG)
    else
        ResourcesProvider.getString(R.string.copy_product_grams, grams)
}

fun BaseProduct.canBeIncremented(): Boolean = validIncrementer(
        if (getSaleType() == WEIGHT_UNIT
                || getSaleType() == UNIT
                || getSaleTypeBasket() == UNIT) {
            getQuantity()
        } else {
            getGrams()
        }
)

private fun BaseProduct.validIncrementer(quantity: Int): Boolean =
        getMaxQuantityGrams() == 0 || quantity < getMaxQuantityGrams()

fun BaseProduct.getGrams(): Int =
        getGrams(getQuantity())

fun BaseProduct.getGrams(amount: Int): Int =
        if (amount > 1) getMinQuantityGrams() + amount * getStepQuantity() - getStepQuantity() else getMinQuantityGrams()

fun BaseProduct.getPriceTotal(): Double = getPriceCalculator().getPrice(this)

fun BaseProduct.isDecrementAvailable(): Boolean =
        getQuantity() - getIncrementer() >= getInitialQuantity()
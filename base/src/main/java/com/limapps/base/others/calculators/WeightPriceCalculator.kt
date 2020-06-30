package com.limapps.base.others.calculators

import com.limapps.base.helpers.getGrams
import com.limapps.base.models.BaseProduct

class WeightPriceCalculator : PriceCalculator {
    companion object {

        private const val KILO = 1000
    }

    override fun getPrice(product: BaseProduct): Double = getPrice(product.getQuantity(), product)

    override fun getPrice(amount: Int, product: BaseProduct): Double {
        return product.getBalancePrice() * product.getGrams() / KILO
    }
}
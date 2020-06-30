package com.limapps.base.others

import com.limapps.base.others.calculators.GeneralCalculator
import com.limapps.base.others.calculators.PriceCalculator
import com.limapps.base.others.calculators.WeightPriceCalculator

object PriceCalculatorFactory {

    fun getPriceCalculator(isWeightBasketSaleType: Boolean): PriceCalculator {
        return if (isWeightBasketSaleType) {
            WeightPriceCalculator()
        } else {
            GeneralCalculator()
        }
    }
}

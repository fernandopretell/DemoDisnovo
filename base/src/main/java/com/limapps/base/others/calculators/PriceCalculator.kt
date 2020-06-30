package com.limapps.base.others.calculators

import com.limapps.base.models.BaseProduct

interface PriceCalculator {

    fun getPrice(product: BaseProduct): Double

    fun getPrice(amount: Int, product: BaseProduct): Double
}

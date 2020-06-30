package com.limapps.base.others.calculators

import com.limapps.base.models.BaseProduct
import com.limapps.base.models.Offer
import com.limapps.base.models.getValue

class GeneralCalculator : PriceCalculator {

    override fun getPrice(product: BaseProduct): Double = getPrice(product.getQuantity(), product)

    override fun getPrice(amount: Int, product: BaseProduct): Double {
        return (product.getPrice() * amount) - getDiscountProduct(amount, product.getOffer(), product.getPrice())
    }

    companion object {

        fun getDiscountProduct(amount: Int, offer: Offer?, price: Double): Double {
            var discount = 0.0

            offer?.let {
                val discountPercentage = it.getValue()

                if (discountPercentage > 0 && it.max > 0) {
                    discount = price / 100 * discountPercentage

                    if (it.max <= amount) {
                        discount *= it.max
                    } else if (it.max > amount) {
                        discount *= amount
                    }
                }
            }

            return discount
        }
    }
}

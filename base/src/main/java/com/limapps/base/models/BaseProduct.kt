package com.limapps.base.models

interface BaseProduct {

    fun getPrice(): Double = 0.0

    fun getQuantity(): Int = 0

    fun getOffer(): Offer? = null

    fun setQuantity(value: Int) {}

    fun getIncrementer(): Int = 0

    fun increment(): Boolean = true

    fun increment(increment: Int): Boolean = true

    fun decrement(): Boolean = true

    fun getMinQuantityGrams(): Int = 0

    fun getMaxQuantityGrams(): Int = 0

    fun getStepQuantity(): Int = 0

    fun getSaleType(): String = ""

    fun getSaleTypeBasket(): String = ""

    fun getBalancePrice(): Double = 0.0

    fun getInitialQuantity(): Int = 0

    fun isValid(): Boolean
}
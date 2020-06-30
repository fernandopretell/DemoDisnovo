package com.limapps.base.repositories

import com.limapps.base.models.ToppingsForProduct
import io.reactivex.Single

interface BaseProductRepository {
    fun getToppingsByProductId(productId: String, storeId: String): Single<ToppingsForProduct>
}
package com.limapps.base.repositories

import com.limapps.common.applySchedulers
import com.limapps.base.models.ToppingsForProduct
import com.limapps.base.rest.retrofit.services.ToppingsService
import io.reactivex.Single
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productService: ToppingsService): BaseProductRepository {

    override fun getToppingsByProductId(productId: String, storeId: String): Single<ToppingsForProduct> =
            productService.getToppingsByProductId(productId, storeId).applySchedulers()
}

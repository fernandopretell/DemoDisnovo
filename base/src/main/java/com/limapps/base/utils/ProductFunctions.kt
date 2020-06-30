package com.limapps.base.utils

private const val ID_SEPARATOR = "_"

fun getStoreProductId(productId: String, storeId: String): String {
    var storeProductId = productId
    if (!productId.contains(ID_SEPARATOR)) {
        storeProductId = storeId + ID_SEPARATOR + productId
    }
    return storeProductId
}

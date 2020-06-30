package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class PersistenceProductsRequest(@SerializedName("products") val products: List<PersistenceProduct>)
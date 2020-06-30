package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class ListStoreId(@SerializedName("store_ids") val storeIds: List<Int> = emptyList())
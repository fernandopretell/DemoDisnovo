package com.limapps.base.models

import com.google.gson.annotations.SerializedName

data class StoresDetailRequest(@SerializedName("store_zone_ids") val storeIds: List<String> = emptyList())
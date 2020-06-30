package com.limapps.base.models.store

import com.google.gson.annotations.SerializedName

data class StoreSubCorridor(@SerializedName("name") val name: String,
                            @SerializedName("icon") val icon: String,
                            @SerializedName("hidden") val hidden: Int,
                            @SerializedName("id") val id: Int,
                            @SerializedName("subcorridor_id") val subCorridorId: Int,
                            @SerializedName("corridor_id") val corridorId: Int,
                            @SerializedName("type") val type: Int,
                            @SerializedName("index") val index: Int)
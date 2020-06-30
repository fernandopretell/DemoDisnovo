package com.limapps.base.models.orders

import android.os.Parcelable
import com.google.gson.JsonObject
import com.limapps.base.managers.FileManager
import com.limapps.base.others.StoreTypeConstants
import com.limapps.common.getIntOrDefault
import com.limapps.common.getStringOrDefault
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreResponse(val id: Int?,
                         val name: String?,
                         val image: String,
                         val type: String?,
                         val storeType: String?) : Parcelable {

    companion object {
        fun fromJsonObject(store: JsonObject): StoreResponse {
            return StoreResponse(
                    store.getIntOrDefault(ID),
                    store.getStringOrDefault(NAME),
                    store.getStringOrDefault(IMAGE) ?: "",
                    store.getStringOrDefault(TYPE),
                    store.getStringOrDefault(STORE_TYPE)
            )
        }
    }
}

fun StoreResponse.getUrlImageForRestaurant(): String {
    return when (type) {
        StoreTypeConstants.RESTAURANT_INFORMAL,
        StoreTypeConstants.RESTAURANT -> FileManager.getRemotePathRestaurantLogo(id?.toString() + FileManager.EXTENSION_PNG)
        else -> ""
    }
}
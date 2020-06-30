package com.limapps.base.models.orders

import android.os.Parcelable
import com.google.gson.JsonObject
import com.limapps.common.getIntOrDefault
import com.limapps.common.getStringOrDefault
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StorekeeperResponse(val id: Int,
                               val name: String?,
                               val phone: String?,
                               val image: String?,
                               val type: String = "") : Parcelable {

    companion object {
        fun fromJsonObject(storekeeper: JsonObject?): StorekeeperResponse {
            return StorekeeperResponse(
                    storekeeper.getIntOrDefault(ID,
                            storekeeper.getIntOrDefault(STOREKEEPER_ID)),
                    storekeeper.getStringOrDefault(NAME)
                            ?: storekeeper.getStringOrDefault(STOREKEEPER_NAME),
                    storekeeper.getStringOrDefault(PHONE)
                            ?: storekeeper.getStringOrDefault(STOREKEEPER_PHONE),
                    storekeeper.getStringOrDefault(IMAGE)
                            ?: storekeeper.getStringOrDefault(STOREKEEPER_PICTURE),
                    storekeeper.getStringOrDefault(TYPE)
                            ?: storekeeper.getStringOrDefault(STOREKEEPER_TYPE) ?: ""
            )
        }
    }
}

fun StorekeeperResponse?.isFakeType(): Boolean {
    return this == null ||
            type.isBlank() ||
            type == FAKE ||
            name?.contains("null", true) == true
}
package com.limapps.base.models.orders

import android.os.Parcelable
import com.google.gson.JsonObject
import com.limapps.common.getIntOrDefault
import com.limapps.common.getStringOrDefault
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExpediterResponse(val id: Int,
                             val name: String?,
                             val image: String) : Parcelable {

    companion object {
        fun fromJsonObject(expediter: JsonObject): ExpediterResponse {
            return ExpediterResponse(
                    expediter.getIntOrDefault(ID, -1),
                    expediter.getStringOrDefault(NAME),
                    expediter.getStringOrDefault(IMAGE) ?: ""
            )
        }
    }
}
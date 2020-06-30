package com.limapps.base.models.orders

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.limapps.base.models.store.DeliveryMethodTypes
import com.limapps.base.utils.getTimestampFromDate
import com.limapps.common.getBooleanOrDefault
import com.limapps.common.getDoubleOrDefault
import com.limapps.common.getJsonObjectOrDefault
import com.limapps.common.getStringOrDefault
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "order_status")
@Parcelize
data class OrderResponse(
        @PrimaryKey
        @ColumnInfo(name = "order_id")
        val id: String,
        val total: Double = 0.0,
        var state: String = "",
        val stateMessage: String = "",
        @SerializedName(PLACE_AT)
        val placeAt: String? = null,
        @SerializedName(PLACE_AT_PATH)
        val placeAtPath: String? = null,
        @SerializedName(DATABASE)
        val placeAtDatabase: String? = null,
        @Embedded(prefix = "expediter_")
        val expediter: ExpediterResponse? = null,
        @Embedded(prefix = "storekeeper_")
        @SerializedName(RT)
        var storekeeper: StorekeeperResponse? = null,
        @Embedded(prefix = "store_")
        val store: StoreResponse? = null,
        val canBeCanceled: Boolean = false,
        val tip: Double = 0.0,
        val finishedAt: Long = 0L,
        val finished: Boolean = false,
        @SerializedName(DELIVERY_METHOD)
        val deliveryMethod: String = DeliveryMethodTypes.DELIVERY.value,
        @SerializedName(ORDER_PLACE_AT)
        val orderPlaceAt: String? = null
) : Parcelable {

    companion object {
        fun fromJsonObject(order: JsonObject): OrderResponse {
            return OrderResponse(
                    order.getStringOrDefault(ID) ?: "",
                    order.getDoubleOrDefault(TOTAL) ?: 0.0,
                    order.getStringOrDefault(STATE) ?: "",
                    order.getStringOrDefault(STATE_MESSAGE) ?: "",
                    order.getStringOrDefault(PLACE_AT),
                    order.getStringOrDefault(PLACE_AT_PATH),
                    order.getStringOrDefault(DATABASE),
                    order.getJsonObjectOrDefault(EXPEDITER)?.let { ExpediterResponse.fromJsonObject(it) },
                    order.getJsonObjectOrDefault(RT)?.let { StorekeeperResponse.fromJsonObject(it) },
                    order.getJsonObjectOrDefault(STORE)?.let { StoreResponse.fromJsonObject(it) },
                    order.getBooleanOrDefault(CAN_BE_CANCEL),
                    order.getDoubleOrDefault(TIP) ?: 0.0,
                    getTimestampFromDate(order.getStringOrDefault(FINISHED_AT) ?: ""),
                    deliveryMethod = order.getStringOrDefault(DELIVERY_METHOD)
                            ?: DeliveryMethodTypes.DELIVERY.value,
                    orderPlaceAt = order.getStringOrDefault(ORDER_PLACE_AT)
            )
        }
    }

}

fun OrderResponse.getSafePlaceAt(): String {
    return orderPlaceAt ?: placeAt.orEmpty()
}
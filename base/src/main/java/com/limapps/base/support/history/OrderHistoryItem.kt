package com.limapps.base.support.history

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.base.models.ActionPoint
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderHistoryItem(@SerializedName("id") val orderId: String = "",
                            @SerializedName("total_value") val totalValue: Double = 0.0,
                            @SerializedName("created_at") val createdAt: String = "",
                            @SerializedName("products") val productList: List<OrderHistoryProduct>? = emptyList(),
                            @SerializedName("whim") val whim: String = "",
                            @SerializedName("store") var store: OrderHistoryStore? = null,
                            @SerializedName("address") val address: OrderHistoryAddress,
                            @SerializedName("calculated_information") val calculatedInformation: CalculatedInformationModel,
                            @SerializedName("reference_point") val referencePoint: ReferencePointModel? = null,
                            @SerializedName("state") val state: String = "",
                            @SerializedName("whims") val whimList: List<OrderHistoryWhim>? = emptyList(),
                            @SerializedName("action_points") val actionPoints: List<ActionPoint>? = emptyList(),
                            @SerializedName("rate") val rate: Long = 0L) : Parcelable


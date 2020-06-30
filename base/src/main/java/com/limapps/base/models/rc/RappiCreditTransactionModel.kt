package com.limapps.base.models.rc

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.base.adapters.GenericAdapterRecyclerView
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RappiCreditTransactionModel(
        @SerializedName("amount")
        val amount: Double,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("ends_at")
        val endsAt: String?,
        @SerializedName("expired")
        val expired: Boolean,
        @SerializedName("gift")
        val gift: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("message")
        val message: String?,
        @SerializedName("only_shipping")
        val onlyShipping: Boolean,
        @SerializedName("order_id")
        val orderId: Int,
        @SerializedName("starts_at")
        val startsAt: String?,
        @SerializedName("state")
        val state: String,
        @SerializedName("tag")
        val tag: String?
) : Parcelable
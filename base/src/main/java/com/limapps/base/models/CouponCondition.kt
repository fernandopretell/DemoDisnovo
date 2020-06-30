package com.limapps.base.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CouponCondition(
    @SerializedName("message")
    val message: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("value")
    val value: String?
) : Parcelable
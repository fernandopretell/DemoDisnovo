package com.limapps.base.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CouponCode(@SerializedName("quantity") val quantity: Int = 0,
                      @SerializedName("left_quantity") val left_quantity: Int = 0,
                      @SerializedName("code") val code: String? = ""
) : Parcelable
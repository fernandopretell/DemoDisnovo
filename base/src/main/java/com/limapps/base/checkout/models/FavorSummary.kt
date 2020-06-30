package com.limapps.base.checkout.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.base.models.ActionPoint
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavorSummary(@SerializedName("action_points")
                        val actionPoints: List<ActionPoint>,
                        val price: Double = 0.0,
                        val distance: Double,
                        @SerializedName("declared_value")
                        val declaredValue: Double = 0.0) : Parcelable
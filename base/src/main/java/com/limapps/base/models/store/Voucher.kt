package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Voucher(@SerializedName("image") val image: String): Parcelable
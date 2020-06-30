package com.limapps.base.checkout.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Information(@SerializedName("cash")val cash:String?) : Parcelable
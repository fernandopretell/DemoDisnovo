package com.limapps.base.checkout.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParameterModel(val key: String, val value: String) : Parcelable
package com.limapps.base.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BannnerTitle(
        @SerializedName("es") val spanishValue: String? = "",
        @SerializedName("en") val englishValue: String? = "",
        @SerializedName("pt") val portugueseValue: String? = ""
) : Parcelable

package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuperTagInformation(@SerializedName("title") val title: String = " ",
                               @SerializedName("body") val items: List<SuperTagInformationItem> = emptyList()) : Parcelable
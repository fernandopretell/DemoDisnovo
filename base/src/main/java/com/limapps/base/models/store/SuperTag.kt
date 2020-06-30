package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuperTag(@SerializedName("image") val image: String = "",
                    @SerializedName("copy") val copy: String = "",
                    @SerializedName("color") val color: String = " ",
                    @SerializedName("information") val information: SuperTagInformation? = null) : Parcelable

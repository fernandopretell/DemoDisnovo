package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.base.models.store.rappibox.RappiBox
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Experiments(
        @SerializedName("super_tag") val superTag: SuperTag? = null,
        @SerializedName("show_rappi_pop") val rappiBox: RappiBox? = null,
        @SerializedName("vouchers") val vouchers: Map<String, Voucher>? = null
) : Parcelable
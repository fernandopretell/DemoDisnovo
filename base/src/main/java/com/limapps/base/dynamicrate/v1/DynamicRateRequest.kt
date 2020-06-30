package com.limapps.base.dynamicrate.v1

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class DynamicRateRequest(@SerializedName("store_id")
                         internal var storeId: String?,
                         @SerializedName("id")
                         internal var id: String?
) : Parcelable

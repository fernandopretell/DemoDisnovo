package com.limapps.base.models.store

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class StoreBase(@SerializedName("store_id") val id: String?,
                     @SerializedName("zone_id") val zoneId: String?,
                     @SerializedName("store_zone_id") val storeZoneId: String?) : Parcelable {
    constructor(source: Parcel) : this(
            id = source.readString(),
            zoneId = source.readString(),
            storeZoneId = source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(zoneId)
        writeString(storeZoneId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StoreBase> = object : Parcelable.Creator<StoreBase> {
            override fun createFromParcel(source: Parcel): StoreBase = StoreBase(source)
            override fun newArray(size: Int): Array<StoreBase?> = arrayOfNulls(size)
        }
    }
}
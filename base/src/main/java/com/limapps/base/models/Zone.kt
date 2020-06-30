package com.limapps.base.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Zone(@SerializedName("name") val name: String? = "",
                @SerializedName("id") val id: String? = "",
                @SerializedName("zone_type") val zoneType: String? = "",
                @SerializedName("polygon") val polygon: List<PointMap> = emptyList()) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
        source.createTypedArrayList(PointMap.CREATOR)!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(id)
        writeString(zoneType)
        writeTypedList(polygon)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Zone> = object : Parcelable.Creator<Zone> {
            override fun createFromParcel(source: Parcel): Zone = Zone(source)
            override fun newArray(size: Int): Array<Zone?> = arrayOfNulls(size)
        }
    }
}
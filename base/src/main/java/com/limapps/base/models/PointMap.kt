package com.limapps.base.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class PointMap(@SerializedName("lat")
               val latitude: Double = 0.0,
               @SerializedName("lng")
               val longitude: Double = 0.0) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readDouble())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PointMap> {
        override fun createFromParcel(parcel: Parcel): PointMap {
            return PointMap(parcel)
        }

        override fun newArray(size: Int): Array<PointMap?> {
            return arrayOfNulls(size)
        }
    }
}
package com.limapps.base.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ToppingUnit(@SerializedName("id") val id: Long,
                       @SerializedName("units") val units: Int): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeInt(units)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ToppingUnit> {
        override fun createFromParcel(parcel: Parcel): ToppingUnit {
            return ToppingUnit(parcel)
        }

        override fun newArray(size: Int): Array<ToppingUnit?> {
            return arrayOfNulls(size)
        }
    }
}
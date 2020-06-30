package com.limapps.base.checkout.models

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator

data class ScheduleInformation(
        val expectedDeliveryDate: String,
        val expectedDeliveryWindow: String,
        val deliveryPrice: Double,
        val startTime: String? = null,
        val endTime: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(expectedDeliveryDate)
        parcel.writeString(expectedDeliveryWindow)
        parcel.writeDouble(deliveryPrice)
        parcel.writeString(startTime)
        parcel.writeString(endTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<ScheduleInformation> {
        override fun createFromParcel(parcel: Parcel): ScheduleInformation {
            return ScheduleInformation(parcel)
        }

        override fun newArray(size: Int): Array<ScheduleInformation?> {
            return arrayOfNulls(size)
        }
    }
}
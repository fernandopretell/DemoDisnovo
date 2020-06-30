package com.limapps.base.checkout.models

import android.os.Parcel
import android.os.Parcelable

data class CheckoutPromise(val hasPromise: Boolean, val promisedEta: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (hasPromise) 1 else 0)
        parcel.writeInt(promisedEta)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CheckoutPromise> {
        override fun createFromParcel(parcel: Parcel): CheckoutPromise {
            return CheckoutPromise(parcel)
        }

        override fun newArray(size: Int): Array<CheckoutPromise?> {
            return arrayOfNulls(size)
        }
    }
}
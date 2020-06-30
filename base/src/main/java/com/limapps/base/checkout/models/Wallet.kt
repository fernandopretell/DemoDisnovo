package com.limapps.base.checkout.models

import android.os.Parcel
import android.os.Parcelable

data class Wallet(
    val type: String?,
    val token: String?
): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(token)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Wallet> {
        override fun createFromParcel(parcel: Parcel): Wallet {
            return Wallet(parcel)
        }

        override fun newArray(size: Int): Array<Wallet?> {
            return arrayOfNulls(size)
        }
    }
}
package com.limapps.base.models.store

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SuperTagInformationItem(@SerializedName("image") val image: String = "",
                                   @SerializedName("copy") val copy: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(copy)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SuperTagInformationItem> {
        override fun createFromParcel(parcel: Parcel): SuperTagInformationItem {
            return SuperTagInformationItem(parcel)
        }

        override fun newArray(size: Int): Array<SuperTagInformationItem?> {
            return arrayOfNulls(size)
        }
    }
}
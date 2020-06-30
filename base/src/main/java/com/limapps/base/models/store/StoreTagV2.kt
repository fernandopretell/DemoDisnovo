package com.limapps.base.models.store

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.base.interfaces.Taggable

data class StoreTagV2(@SerializedName("id") override val id: Int,
                      @SerializedName("name") override val name: String,
                      @SerializedName("index") override val index: Int,
                      @SerializedName("image") val image: String?,
                      @SerializedName("icon") val icon: String?) : Parcelable, Taggable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString().toString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(index)
        parcel.writeString(image ?: "")
        parcel.writeString(icon)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StoreTagV2> = object : Parcelable.Creator<StoreTagV2> {
            override fun createFromParcel(parcel: Parcel): StoreTagV2 = StoreTagV2(parcel)
            override fun newArray(size: Int): Array<StoreTagV2?> = arrayOfNulls(size)
        }
    }
}
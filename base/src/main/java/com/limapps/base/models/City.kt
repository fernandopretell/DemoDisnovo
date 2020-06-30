package com.limapps.base.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class City(
    val id: Int,
    val description: String? = "",
    val flag: String? = "",
    val city: String? = "",
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double,
    @SerializedName("code_iso_2") val codeIso: String? = ""
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readDouble(),
            source.readDouble(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(description)
        writeString(flag)
        writeString(city)
        writeDouble(latitude)
        writeDouble(longitude)
        writeString(codeIso)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<City> = object : Parcelable.Creator<City> {
            override fun createFromParcel(source: Parcel): City = City(source)
            override fun newArray(size: Int): Array<City?> = arrayOfNulls(size)
        }
    }
}
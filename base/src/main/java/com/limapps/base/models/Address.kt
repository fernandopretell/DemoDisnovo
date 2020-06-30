package com.limapps.base.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.base.location.Location
import com.limapps.common.upperCaseFirstLetter

data class Address(
    val id: Int = -1,
    val address: String = "",
    @SerializedName(value = "tag", alternate = ["address_tag"])
    val tag: String? = "",
    @SerializedName(value = "description", alternate = ["address_description"])
    val description: String? = "",
    @SerializedName(value = "active")
    val isActive: Boolean = false,
    @SerializedName(value = "lat")
    val latitude: Double = 0.0,
    @SerializedName(value = "lng")
    val longitude: Double = 0.0,
    @SerializedName("count_orders")
    val orders: Int = 0,
    @SerializedName("lastorder")
    val days: Int = 0,
    @SerializedName("zone")
    val zone: Zone? = null,
    @SerializedName(value = "country")
    val country: String? = "",
    @SerializedName(value = "city")
    val city: City? = null,
    @SerializedName(value = "api_google_data")
    val googleData: String? = null,
    @Transient
    val placeId: String? = null
) : Parcelable {

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString().toString(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            source.readDouble(),
            source.readDouble(),
            source.readInt(),
            source.readInt(),
            source.readParcelable<Zone>(Zone::class.java.classLoader),
            source.readString(),
            source.readParcelable<City>(City::class.java.classLoader),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(address)
        writeString(tag)
        writeString(description)
        writeInt((if (isActive) 1 else 0))
        writeDouble(latitude)
        writeDouble(longitude)
        writeInt(orders)
        writeInt(days)
        writeParcelable(zone, 0)
        writeString(country)
        writeParcelable(city, 0)
        writeString(googleData)
        writeString(placeId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Address> = object : Parcelable.Creator<Address> {
            override fun createFromParcel(source: Parcel): Address = Address(source)
            override fun newArray(size: Int): Array<Address?> = arrayOfNulls(size)
        }
    }

}

fun Address.getAddressOrCityLongitude(): Double {
    return if (longitude != 0.0) {
        longitude
    } else {
        city?.longitude ?: 0.0
    }
}

fun Address.getAddressOrCityLatitude(): Double {
    return if (latitude != 0.0) {
        latitude
    } else {
        city?.latitude ?: 0.0
    }
}

fun Address.getCityName(): String {
    return city?.city ?: zone?.let {
        val zoneName = it.name ?: ""
        val cityIndex = zoneName.indexOfFirst { it == '-' }
        if (cityIndex > 0) {
            zoneName.substring(0, cityIndex).trim()
        } else {
            zoneName.trim()
        }
    }.orEmpty()
}

fun Address.getTagFormatted(): String {
    return tag?.upperCaseFirstLetter().orEmpty()
}

fun Address.toLocation(): Location {
    return Location(this.latitude, this.longitude)
}
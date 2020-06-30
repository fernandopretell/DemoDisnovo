package com.limapps.base.models

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

const val KILOMETERS_METER_FACTOR = 1000

class PlaceSorted(
    val id: String? = "",
    val distance: Float = -1f,
    val primaryText: String? = "",
    val secondaryText: String? = "",
    var location: LatLng? = null,
    val isOpen: Boolean = false,
    val types: List<String> = listOf()
) : Parcelable, Comparable<PlaceSorted> {

    var query: String = ""

    val distanceInKilometers: Double
        get() = (distance / KILOMETERS_METER_FACTOR).toDouble()

    val placeInfo: PlaceInfo? = location?.let {
        PlaceInfo(id, primaryText, it.latitude, it.longitude, types)
    }

    override fun compareTo(other: PlaceSorted): Int {
        val distance1 = distance
        val distance2 = other.distance
        return when {
            distance1 > distance2 -> 1
            distance1 < distance2 -> -1
            else -> 0
        }
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readFloat(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(LatLng::class.java.classLoader),
            parcel.readByte() != 0.toByte(),
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeFloat(distance)
        parcel.writeString(primaryText)
        parcel.writeString(secondaryText)
        parcel.writeParcelable(location, flags)
        parcel.writeByte(if (isOpen) 1 else 0)
        parcel.writeStringList(types)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlaceSorted> {
        override fun createFromParcel(parcel: Parcel): PlaceSorted {
            return PlaceSorted(parcel)
        }

        override fun newArray(size: Int): Array<PlaceSorted?> {
            return arrayOfNulls(size)
        }
    }
}
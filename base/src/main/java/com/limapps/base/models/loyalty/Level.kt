package com.limapps.base.models.loyalty

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Level(
        @SerializedName("id")
        val id: Long,
        @SerializedName("start_color")
        val startColor: String?,
        @SerializedName("end_color")
        val endColor: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("limit_lower")
        val limit: Int,
        @SerializedName("limit_higher")
        val limitHigher: Int,
        @SerializedName("name")
        val name: String?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("active")
        val active: Boolean,
        @SerializedName("upgrade_copy")
        val upgradeCopy: String? = "",

        // Additional ETA fields.
        @SerializedName("ends_at")
        val periodEndsAt: String?
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(startColor)
        parcel.writeString(endColor)
        parcel.writeString(description)
        parcel.writeInt(limit)
        parcel.writeInt(limitHigher)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(image)
        parcel.writeByte(if (active) 1 else 0)
        parcel.writeString(upgradeCopy)
        parcel.writeString(periodEndsAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Level> {
        override fun createFromParcel(parcel: Parcel): Level {
            return Level(parcel.readLong(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readInt(),
                    parcel.readInt(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readByte() != 0.toByte(),
                    parcel.readString(),
                    parcel.readString())
        }

        override fun newArray(size: Int): Array<Level?> {
            return arrayOfNulls(size)
        }
    }
}

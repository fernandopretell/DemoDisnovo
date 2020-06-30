package com.limapps.base.support.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.limapps.common.getDoubleOrDefault
import com.limapps.common.getLongOrDefault
import com.limapps.common.getStringOrDefault

data class ToppingsList(
        val toppings: List<Toppings>?
) : Parcelable {

    constructor(toppings: JsonArray) : this(
            toppings.map {
                when (it) {
                    is JsonObject -> Toppings(it.asJsonObject)
                    else -> Toppings(
                            id = 0L,
                            description = it.asString,
                            price = -1.0
                    )
                }

            }
    )

    constructor(source: Parcel) : this(
            source.createTypedArrayList(Toppings.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(toppings)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ToppingsList> = object : Parcelable.Creator<ToppingsList> {
            override fun createFromParcel(source: Parcel): ToppingsList = ToppingsList(source)
            override fun newArray(size: Int): Array<ToppingsList?> = arrayOfNulls(size)
        }
    }
}

data class Toppings(
    @SerializedName("id")
    val id: Long,
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("price")
    val price: Double
) : Parcelable {

    constructor(toppings: JsonObject) : this(
            toppings.getLongOrDefault("id") ?: 0L,
            toppings.asJsonObject.getStringOrDefault("description").orEmpty(),
            toppings.asJsonObject.getDoubleOrDefault("price") ?: 0.0
    )

    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(description)
        writeDouble(price)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Toppings> = object : Parcelable.Creator<Toppings> {
            override fun createFromParcel(source: Parcel): Toppings = Toppings(source)
            override fun newArray(size: Int): Array<Toppings?> = arrayOfNulls(size)
        }
    }
}
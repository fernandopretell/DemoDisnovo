package com.limapps.base.models.store

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class StoreInformation(
        @SerializedName(value = "eta_value", alternate = ["eta"]) var eta: Int,
        @SerializedName("store_id") var storeId: Int,
        @SerializedName("has_promise") var hasPromise: Boolean,
        @SerializedName("delivery_price") var deliveryPrice: Double = 0.0,
        @SerializedName("max_quantity") var maxQuantity: Int,
        @SerializedName("version") var version: Int,
        @SerializedName("landing_classification") var classification: String? = null,
        @SerializedName("percentage_service_fee") val percentageServiceFee: Double = 0.0
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(eta)
        parcel.writeInt(storeId)
        parcel.writeByte(if (hasPromise) 1 else 0)
        parcel.writeDouble(deliveryPrice)
        parcel.writeInt(maxQuantity)
        parcel.writeInt(version)
        parcel.writeString(classification)
        parcel.writeDouble(percentageServiceFee)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<StoreInformation> {
        override fun createFromParcel(parcel: Parcel): StoreInformation = StoreInformation(parcel)

        override fun newArray(size: Int): Array<StoreInformation?> = arrayOfNulls(size)
    }
}
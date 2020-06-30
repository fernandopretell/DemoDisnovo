package com.limapps.base.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CouponProduct(@SerializedName("productId") val productId: String?,
                         @SerializedName("value") val value: String? = "0",
                         @SerializedName("price") val price: Double,
                         @SerializedName("max_quantity") val maxQuantity: String? = "1",
                         @SerializedName("quantity_with_discount") val quantityWithDiscount: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productId)
        parcel.writeString(value)
        parcel.writeDouble(price)
        parcel.writeString(maxQuantity)
        parcel.writeInt(quantityWithDiscount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CouponProduct> {
        override fun createFromParcel(parcel: Parcel): CouponProduct {
            return CouponProduct(parcel)
        }

        override fun newArray(size: Int): Array<CouponProduct?> {
            return arrayOfNulls(size)
        }
    }
}
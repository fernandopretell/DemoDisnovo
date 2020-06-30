package com.limapps.base.support.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SupportInfoProduct(
    @SerializedName("product_id")
    val productId: String? = "",
    @SerializedName("product_name")
    val productName: String? = "",
    @SerializedName("price")
    var paidPrice: Double = 0.0,
    @SerializedName("quantity")
    var quantity: Int = 0,
    @SerializedName("image")
    var image: String?,
    @SerializedName("toppings")
    var toppings: List<Toppings>? = null
) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readDouble(),
            source.readInt(),
            source.readString(),
            source.createTypedArrayList(Toppings.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(productId)
        writeString(productName)
        writeDouble(paidPrice)
        writeInt(quantity)
        writeString(image)
        writeTypedList(toppings)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SupportInfoProduct> = object : Parcelable.Creator<SupportInfoProduct> {
            override fun createFromParcel(source: Parcel): SupportInfoProduct = SupportInfoProduct(source)
            override fun newArray(size: Int): Array<SupportInfoProduct?> = arrayOfNulls(size)
        }
    }
}
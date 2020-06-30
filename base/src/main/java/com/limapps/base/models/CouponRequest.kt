package com.limapps.base.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class CouponRequest(@SerializedName("code") val code: String?,
                         @SerializedName("lat") val lat: Double,
                         @SerializedName("lng") val lng: Double,
                         @SerializedName("store_types") val storeTypes: ArrayList<String>?
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(code)
        dest.writeDouble(lat)
        dest.writeDouble(lng)
        dest.writeStringList(storeTypes)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CouponRequest> = object : Parcelable.Creator<CouponRequest> {
            override fun createFromParcel(parcelIn: Parcel): CouponRequest {
                return CouponRequest(parcelIn.readString(), parcelIn.readDouble(), parcelIn.readDouble(),
                        parcelIn.createStringArrayList())
            }

            override fun newArray(size: Int): Array<CouponRequest?> {
                return arrayOfNulls(size)
            }
        }
    }
}
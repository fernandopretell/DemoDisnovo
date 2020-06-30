package com.limapps.base.credits
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RappiCredits(
    @SerializedName("amount") val amount: Double = 0.0,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("shipping") val shipping: Double = 0.0
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readByte() != 0.toByte(),
            parcel.readDouble())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(amount)
        parcel.writeByte(if (isActive) 1 else 0)
        parcel.writeDouble(shipping)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RappiCredits> {
        override fun createFromParcel(parcel: Parcel): RappiCredits {
            return RappiCredits(parcel)
        }

        override fun newArray(size: Int): Array<RappiCredits?> {
            return arrayOfNulls(size)
        }
    }
}

sealed class CreditsActions {
    class OnSuccess(val credits: RappiCredits) : CreditsActions()
    object OnError : CreditsActions()
    object OnLoading : CreditsActions()
}
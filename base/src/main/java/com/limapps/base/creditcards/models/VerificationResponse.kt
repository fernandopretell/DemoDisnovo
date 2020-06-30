package com.limapps.base.creditcards.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class VerificationResponse(@SerializedName("id")
                                val id: Int,
                                @SerializedName("accountId")
                                val accountId: Int,
                                @SerializedName("integerDigits")
                                val integerDigits: Int,
                                @SerializedName("decimalDigits")
                                val decimalDigits: Int,
                                @SerializedName("gatewaySoftname")
                                val gatewayName: String,
                                @SerializedName("time")
                                val time: String,
                                @SerializedName("date")
                                val date: String,
                                @SerializedName("currencyCode")
                                val currencyCode: String,
                                @SerializedName("pendingRetries")
                                val pendingRetries: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readString().orEmpty(),
            source.readString().orEmpty(),
            source.readString().orEmpty(),
            source.readString().orEmpty(),
            source.readString().orEmpty()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeInt(accountId)
        writeInt(integerDigits)
        writeInt(decimalDigits)
        writeString(gatewayName)
        writeString(time)
        writeString(date)
        writeString(currencyCode)
        writeString(pendingRetries)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VerificationResponse> = object : Parcelable.Creator<VerificationResponse> {
            override fun createFromParcel(source: Parcel): VerificationResponse = VerificationResponse(source)
            override fun newArray(size: Int): Array<VerificationResponse?> = arrayOfNulls(size)
        }
    }
}
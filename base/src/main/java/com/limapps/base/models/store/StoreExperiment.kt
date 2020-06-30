package com.limapps.base.models.store

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.annotations.SerializedName

data class StoreExperiment(@SerializedName("key") val key: String? = "",
                           @SerializedName("variant") val variant: String? = "",
                           @SerializedName("variant_id") val variantId: Long = 0,
                           @SerializedName("experiment_id") val experimentId: Long = 0,
                           @SerializedName("payload") val payload: JsonElement? = null) : Parcelable {
    constructor(source: Parcel) : this(
            key = source.readString(),
            variant = source.readString(),
            variantId = source.readLong(),
            experimentId = source.readLong(),
            payload = JsonParser().parse(source.readString()))


    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(key)
        writeString(variant)
        writeLong(variantId)
        writeLong(experimentId)
        writeString(payload?.toString())
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StoreExperiment> = object : Parcelable.Creator<StoreExperiment> {
            override fun createFromParcel(source: Parcel): StoreExperiment = StoreExperiment(source)
            override fun newArray(size: Int): Array<StoreExperiment?> = arrayOfNulls(size)
        }
    }
}

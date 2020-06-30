package com.limapps.base.models.store

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.common.tryOrDefault

data class StoreTypeModelV2(@SerializedName("name") val name: String?,
                            @SerializedName("description") val description: String?,
                            @SerializedName("store_type") val storeType: String?,
                            @SerializedName("image") val image: String? = null,
                            @SerializedName("scheduled") val scheduled: Boolean = false,
                            @SerializedName("shows_button") val showButton: Boolean = false,
                            @SerializedName("validate_access") val validateAccess: Boolean = false,
                            @SerializedName("stores") val stores: List<StoreBase> = emptyList(),
                            @SerializedName("home_type") val homeType: String? = "",
                            @SerializedName("suboptions") val subOptions: List<StoreTypeModelV2> = emptyList(),
                            @SerializedName("sub_title") val subTitle: String? = null,
                            @SerializedName("banner_image") val bannerImage: String? = null,
                            @SerializedName("group") val group: String? = null,
                            @SerializedName("sub_group") val subGroup: String? = null,
                            @SerializedName("pinned_index") val pinnedIndex: Int? = null,
                            @SerializedName("pinned") val pinned: Boolean? = null,
                            @SerializedName("is_hidden") val isHidden: Boolean? = null,
                            @SerializedName("extra_information") val extraInformation: Map<String, Any>? = null) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            1 == source.readInt(),
            1 == source.readInt(),
        source.createTypedArrayList(StoreBase.CREATOR)!!,
            source.readString(),
        source.createTypedArrayList(StoreTypeModelV2.CREATOR)!!,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(description)
        writeString(storeType)
        writeString(image)
        writeInt((if (scheduled) 1 else 0))
        writeInt((if (showButton) 1 else 0))
        writeInt((if (validateAccess) 1 else 0))
        writeTypedList(stores)
        writeString(homeType)
        writeTypedList(subOptions)
        writeString(subTitle)
        writeString(bannerImage)
        writeString(group)
        writeString(subGroup)
    }

    fun getRequireUser(): Boolean {
        return tryOrDefault({extraInformation?.get("require_user") as Boolean}, false)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StoreTypeModelV2> = object : Parcelable.Creator<StoreTypeModelV2> {
            override fun createFromParcel(source: Parcel): StoreTypeModelV2 = StoreTypeModelV2(source)
            override fun newArray(size: Int): Array<StoreTypeModelV2?> = arrayOfNulls(size)
        }
    }
}
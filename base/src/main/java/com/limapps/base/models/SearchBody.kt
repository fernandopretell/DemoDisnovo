package com.limapps.base.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SearchBody(@SerializedName("query") var query: String,
                      @SerializedName("stores") var stores: List<Int>,
                      @SerializedName("options") var options: SearchModel.SearchOptions? = null,
                      @Transient var page: Int = 1) : Parcelable {
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(query)
        dest.writeList(stores)
        dest.writeInt(page)
        dest.writeValue(options)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<SearchBody> = object : Parcelable.Creator<SearchBody> {
            override fun createFromParcel(parcelIn: Parcel): SearchBody {
                val query = parcelIn.readString()
                val stores = arrayListOf<Int>()
                val page = 1
                parcelIn.readList(stores as List<*>, Int::class.java.classLoader)
                return SearchBody(query.toString(), stores, null, page)
            }

            override fun newArray(size: Int): Array<SearchBody?> = arrayOfNulls(size)
        }
    }
}
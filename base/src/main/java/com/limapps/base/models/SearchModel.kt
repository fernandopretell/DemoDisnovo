package com.limapps.base.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.base.models.store.StoreSchedule
import com.limapps.base.models.store.StoreTagV2
import java.io.Serializable

sealed class SearchModel {

    data class Schedule(@SerializedName("store_id") var storeId: Int,
                        @SerializedName("open_time") var startsTime: String,
                        @SerializedName("close_time") var endsTime: String,
                        @SerializedName("days") var days: String) : Serializable

    data class SearchStore(@SerializedName("name") val name: String,
                           @SerializedName("image") val image: String,
                           @SerializedName("background") val background: String,
                           @SerializedName("store_id") val storeId: Int,
                           @SerializedName("split_order") val splitOrder: Boolean,
                           @SerializedName("schedules") val schedules: List<StoreSchedule>,
                           @SerializedName("store_type") val storeType: String,
                           @SerializedName("version") val version: Int,
                           @SerializedName("logo") val logo: String?,
                           @SerializedName("id") val id: Int = 0,
                           @SerializedName("eta") val eta: String,
                           @SerializedName("categories") val categories: List<StoreTagV2> = emptyList(),
                           @SerializedName("type") val type: String,
                           @SerializedName("index") val index: Int = 0) : Serializable

    data class SearchSort(@SerializedName("field") val field: String?,
                          @SerializedName("order") val order: String?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(field)
            parcel.writeString(order)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SearchSort> {
            override fun createFromParcel(parcel: Parcel): SearchSort {
                return SearchSort(parcel)
            }

            override fun newArray(size: Int): Array<SearchSort?> {
                return arrayOfNulls(size)
            }
        }
    }

    data class SearchOptions(@SerializedName("sort") val sort: List<SearchSort>)

    data class DidYouMean(@SerializedName("category_suggest") val categorySuggestions: List<Suggestion>,
                          @SerializedName("name_suggest") val nameSuggestions: List<Suggestion>)

    data class Suggestion(@SerializedName("text") val text: String,
                          @SerializedName("offset") val offset: Int,
                          @SerializedName("length") val length: Int,
                          @SerializedName("options") val options: List<SuggestionOptions>)

    data class SuggestionOptions(@SerializedName("text") val text: String,
                                 @SerializedName("score") val score: Number)
}




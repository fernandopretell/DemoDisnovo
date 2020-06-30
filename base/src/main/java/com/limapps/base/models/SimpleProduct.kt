package com.limapps.base.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.base.adapters.GenericAdapterRecyclerView
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SimpleProduct(@SerializedName("id")
                         val id: String,
                         @SerializedName("name")
                         val name: String,
                         @SerializedName("image")
                         val image: String,
                         @SerializedName("price")
                         val price: Double,
                         @SerializedName("units")
                         val units: Int,
                         @SerializedName("available")
                         val available: Boolean = false,
                         @SerializedName("valid")
                         val valid: Boolean = false) : GenericAdapterRecyclerView.ItemModel<SimpleProduct>, Parcelable {
    override fun getData(): SimpleProduct = this

    override fun setData(data: SimpleProduct?) = Unit

    override fun getViewType(): Int = 0

}
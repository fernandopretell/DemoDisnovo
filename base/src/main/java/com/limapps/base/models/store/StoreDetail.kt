package com.limapps.base.models.store

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.limapps.base.models.Saturation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreDetail(
        @SerializedName("price_index") val priceIndex: Int = 0,
        @SerializedName("price_range") val priceRange: String = "",
        @SerializedName("eta") val eta: String = "",
        @SerializedName("eta_value") val etaValue: Int = 0,
        @SerializedName("schedules") val schedules: List<StoreSchedule> = emptyList(),
        @SerializedName("logo") val logo: String? = "",
        @SerializedName("id") val id: String = "",
        @SerializedName("store_id") val storeId: Int,
        @SerializedName("is_open_today") val isOpenToday: Boolean = true,
        @SerializedName("index") val index: Int = 0,
        @SerializedName("brand_name") val brandName: String = "",
        @SerializedName("close_time") val closeTime: String? = "",
        @SerializedName("store_type") val storeType: String = "",
        @SerializedName("has_promise") val hasPromise: Boolean = false,
        @SerializedName("tags") val tags: List<Int> = emptyList(),
        @SerializedName("background") val background: String = "",
        @SerializedName("name") val name: String = "",
        @SerializedName("percentage_service_fee") val percentageServiceFee: Double = 0.0,
        @SerializedName("is_marketplace") val isMarketPlace: Boolean = false,
        @SerializedName("delivery_price") val deliveryPrice: Double = 0.0,
        @SerializedName("has_comments") val hasComments: Boolean = true,
        @SerializedName("experiments") val experiments: List<StoreExperiment> = emptyList(),
        @SerializedName("discount_tags") val discountTags: List<DiscountTag> = emptyList(),
        @SerializedName("rating") val rating: Rating = Rating(),
        @SerializedName("saturation") val saturation: Saturation? = null,
        @SerializedName("is_currently_available") val isCurrentlyAvailable: Boolean = true,
        @SerializedName("delivery_methods") val deliveryMethods: List<String> = emptyList(),
        @SerializedName("location") val location: List<Double> = emptyList(),
        @SerializedName("address") val address: String = "",
        @SerializedName("avg_price") val averagePrice: Double = 0.0,
        @SerializedName("brand_id") val brandId: Long = 0) : Parcelable

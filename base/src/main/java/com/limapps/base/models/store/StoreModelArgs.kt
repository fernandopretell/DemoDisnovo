package com.limapps.base.models.store

import android.os.Parcelable
import com.limapps.base.analytics.model.AnalyticsEvent.STORE_ID
import com.limapps.base.analytics.model.AnalyticsEvent.STORE_NAME
import com.limapps.base.analytics.model.AnalyticsEvent.STORE_TYPE
import com.limapps.base.analytics.model.AnalyticsEvent.VERTICAL_GROUP
import com.limapps.base.analytics.model.AnalyticsEvent.VERTICAL_SUB_GROUP
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreModelArgs(
    val storeId: String,
    val storeType: String?,
    val homeType: String?,
    val storeName: String?,
    val scheduled: Boolean,
    val group: String?,
    val subGroup: String?,
    val offer: OfferStoreModel? = null,
    val eta: DeliveryEta? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val placeId: String? = null
) : Parcelable


fun StoreModelArgs.getAnalytic(): HashMap<String, String> {
    return HashMap<String, String>().apply {
        put(STORE_ID, storeId)
        storeType?.let { put(STORE_TYPE, it) }
        storeName?.let { put(STORE_NAME, it) }
        group?.let { put(VERTICAL_GROUP, it) }
        subGroup?.let { put(VERTICAL_SUB_GROUP, it) }
    }
}

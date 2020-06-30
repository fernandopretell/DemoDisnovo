package com.limapps.base.utils

import com.google.gson.annotations.SerializedName
import com.limapps.base.models.Address
import com.limapps.base.models.SimpleProduct
import com.limapps.base.remoteconfig.RemoteConfigManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

@Singleton
class AddressNotifier {

    companion object {
        private var remoteConfig: RemoteConfigManager? = null
        private val action: PublishSubject<AddressActions> by lazy { PublishSubject.create<AddressActions>() }

        fun shouldShowChangeLocation(): Boolean {
            return (remoteConfig?.getValue(ENABLE_CHANGE_SHOPPING_LOCATION)?.asJson(RemoteConfigChangeLocation::class.java))?.active == true
        }

        fun setRemoteConfigManager(remoteConfigManager: RemoteConfigManager) {
            this.remoteConfig = remoteConfigManager
        }

        fun changeLocationActions(): Observable<AddressActions> = action.hide()
        fun sendAction(addressActions: AddressActions) {
            action.onNext(addressActions)
        }
    }
}

sealed class AddressActions {
    class ChangeLocation(val storeType: String, val source: String) : AddressActions()
    class ShowAddress(val useCurrentLocationShortcut: Boolean = false) : AddressActions()
    object OpenMissingStore : AddressActions()
    class OpenUnavailableProducts(val invalidProducts: List<SimpleProduct>) : AddressActions()
    object GoHome : AddressActions()
    class CacheLastAddress(val lastAddress: Address) : AddressActions()
    class WithInvalidStoresUserChangesLocation(val addressStoreType: String) : AddressActions()
}

data class RemoteConfigChangeLocation(
        @SerializedName("active") val active: Boolean = false,
        @SerializedName("enabled_store_types") val enabledStoreTypes: ArrayList<String> = arrayListOf()
)

const val ENABLE_CHANGE_SHOPPING_LOCATION = "enable_change_shopping_location"
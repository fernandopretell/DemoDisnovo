package com.limapps.base.remoteconfig

/**
 * Interface to obtain values from the remoteConfig
 */
interface RemoteConfigManager {
    /**
     * returns the value corresponding to the provided key
     * @param key is the key of the desired value
     * @return the value as a RemoteConfigValue
     */
    fun getValue(key: String): RemoteConfigValue
}
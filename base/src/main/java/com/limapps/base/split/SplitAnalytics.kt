package com.limapps.base.split

interface SplitAnalytics {

    fun initUser(userId: String)

    /**
     * Track [event] using defined parameters in [parameters].
     * Refer to [https://help.split.io/hc/en-us/articles/360020343291-Android-SDK#track] for more information.
     */
    fun log(event: String, parameters: Map<String, String> = HashMap())

}

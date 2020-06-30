package com.limapps.base.split

import androidx.annotation.UiThread
import io.reactivex.Observable

interface SplitTreatmentManager {

    /**
     * Determine the treatment to show to the user based on default attributes set.
     * Refer to [https://help.split.io/hc/en-us/articles/360020343291-Android-SDK#basic-usage] for more information.
     */
    @UiThread
    fun getTreatment(splitName: String, customAttributes: Map<String, String> = emptyMap()): Observable<String>

    /**
     * Get a stringified version of the configuration JSON associated to [splitName] in the Split web console.
     * Refer to [https://help.split.io/hc/en-us/articles/360020343291-Android-SDK#get-treatments-with-configurations] for more information.
     */
    @UiThread
    fun getTreatmentConfig(splitName: String, customAttributes: Map<String, String> = emptyMap()): Observable<String>

    /**
     * Unsafe call to determine split config. It may return control clauses.
     */
    @UiThread
    fun <T> getRawTreatmentConfig(splitName: String, clazz: Class<T>, customAttributes: Map<String, String> = emptyMap()): T?

    /**
     * Get the configuration associated to [splitName] in the Split web console in desired format defined by [clazz].
     * Refer to [https://help.split.io/hc/en-us/articles/360020343291-Android-SDK#get-treatments-with-configurations] for more information.
     */
    @UiThread
    fun <T> getTreatmentConfig(splitName: String, clazz: Class<T>, customAttributes: Map<String, String> = emptyMap()): Observable<T>
}
package com.limapps.base.split

import com.limapps.base.di.scopes.ActivityScope

@ActivityScope  // Split updates will be visible after the activity is recreated at least (it also depends on Split refresh rate)
abstract class OnDemandSplitTreatmentProvider<T>(
        val splitTreatmentManager: SplitTreatmentManager
) {

    /**
     * Treatment model class used for deserializing the Split configuration.
     */
    protected abstract fun getTreatmentModelClass(): Class<T>

    /**
     * Split name as presented on Splits dashboard.
     */
    protected abstract fun getSplitName(): String

    /**
     * Provide a custom map of attributes to open space for experimentation.
     */
    protected open fun getCustomAttributes(): Map<String, String> {
        return emptyMap()
    }

    protected fun getTreatment(): T? {
        return splitTreatmentManager.getRawTreatmentConfig(getSplitName(), getTreatmentModelClass(), getCustomAttributes())
    }
}
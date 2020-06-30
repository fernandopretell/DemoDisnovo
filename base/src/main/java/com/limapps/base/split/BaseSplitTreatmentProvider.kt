package com.limapps.base.split

import android.annotation.SuppressLint
import androidx.annotation.UiThread
import com.limapps.base.di.scopes.ActivityScope

@ActivityScope  // Split updates will be visible after the activity is recreated at least (it also depends on Split refresh rate)
abstract class BaseSplitTreatmentProvider<T>(
        val splitTreatmentManager: SplitTreatmentManager
) {

    protected var currentTreatment: T? = null

    init {
        initTreatmentProvider(splitTreatmentManager)
    }

    /**
     * Subscribes to treatment configuration with Split using provided treatment name and model.
     */
    @UiThread
    @SuppressLint("CheckResult")    // Ignored: we're taking only one treatment and then completing the stream
    private fun initTreatmentProvider(splitTreatmentManager: SplitTreatmentManager) {
        splitTreatmentManager
                .getTreatmentConfig(getSplitName(), getTreatmentModelClass(), getCustomAttributes())
                .take(1)
                .subscribe({ currentTreatment = it }, {
                    currentTreatment = getDefaultTreatmentModel()
                    it.printStackTrace()
                })
    }

    /**
     * Default configuration to be used in case of error when querying treatment's configuration.
     */
    protected abstract fun getDefaultTreatmentModel(): T

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
}
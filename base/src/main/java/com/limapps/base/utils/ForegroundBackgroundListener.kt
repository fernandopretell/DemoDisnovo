package com.limapps.base.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.limapps.base.activities.BaseFragmentActivity
import com.limapps.base.models.Address
import com.limapps.base.repositories.AppRepository
import com.limapps.common.className
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ForegroundBackgroundListener @Inject constructor(
    private val appRepository: AppRepository
    //private val countryDataProvider: CountryDataProvider
) : LifecycleObserver {

    var address: Address? = null

    private val compositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeAction() {

        address?.let{ existingAddress ->

            if(existingAddress.address.isNullOrEmpty()) {
                /*val mapLocation = countryDataProvider
                        .getCountryDefaultLocation()*/

//                address = Address(
//                        latitude = mapLocation.first.toDouble(),
//                        longitude = mapLocation.second.toDouble()
//                )
            }
        }

        val latitude = address?.latitude?.toString()
        val longitude = address?.longitude?.toString()
        compositeDisposable.add(
                appRepository.getServerTime(latitude, longitude)
                        .subscribe({
                            setCurrentServerDate(it)
                        }, { throwable -> LogUtil.e(className(), throwable = throwable) })
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        compositeDisposable.clear()
        BaseFragmentActivity.isLongDistanceAllowed = false
    }
}

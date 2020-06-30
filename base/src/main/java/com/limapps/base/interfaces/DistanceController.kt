package com.limapps.base.interfaces

import io.reactivex.Observable

interface DistanceController {

    fun isCurrentAddressFarFromLocation(): Observable<Boolean>
    fun isLocationEnableByUser(): Observable<Boolean>
    fun checkIsFarFromHome(): Observable<Boolean>

}
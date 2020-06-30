package com.limapps.base

import com.limapps.base.location.Location
import io.reactivex.Single

interface CoverageRepository {

    fun addressHasCoverage(location: Location): Single<Boolean>

}
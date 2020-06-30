package com.limapps.base.handlers

import com.limapps.base.models.ReferralMenuInfo
import com.limapps.base.models.ReferredCodeV2
import io.reactivex.Single

interface ReferralCodeController {

    fun getUserReferredCodesV2(): Single<ReferredCodeV2>

    fun getReferralBannerInfo(): Single<ReferralMenuInfo>
}
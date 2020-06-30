package com.limapps.base.support.controllers

import com.limapps.base.support.model.HelpCenterConfig
import io.reactivex.Single

interface HelpCenterSplitController {
    fun getConfig(): Single<HelpCenterConfig>
}
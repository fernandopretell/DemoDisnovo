package com.limapps.base.handlers

import com.limapps.base.activities.BaseFragmentActivity
import com.limapps.base.others.ExtrasKeys.SOURCE_HOME

interface ChangeLocationController {
    fun clean()
    fun init(storeType: String, activity: BaseFragmentActivity, lastSource: String = SOURCE_HOME)
}

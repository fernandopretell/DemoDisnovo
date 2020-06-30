package com.limapps.base.others

import com.limapps.baseui.views.dialogs.BaseDialogFragment.BaseDialogFragmentCallback

interface Callback<ReturnType> : BaseDialogFragmentCallback {
    fun onResolve(resolved: ReturnType)
    fun onReject(error: Throwable?)
    fun onFinish()
}
package com.limapps.baseui.views.dialogs

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheet<T> : BottomSheetDialogFragment() {

    protected var callback: T? = null

    fun setCallback(callback: T): BaseBottomSheet<*> {
        this.callback = callback
        return this
    }


}

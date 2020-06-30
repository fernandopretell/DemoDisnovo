package com.limapps.base.dialogs

import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetDialog : BottomSheetDialogFragment() {


    override fun show(manager: FragmentManager, tag: String?) {
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    override fun dismiss() = dismissAllowingStateLoss()

    fun getDialogTag(): String = this.javaClass.name
}
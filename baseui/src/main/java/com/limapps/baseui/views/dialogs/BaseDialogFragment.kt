package com.limapps.baseui.views.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.Window

open class BaseDialogFragment<T : BaseDialogFragment.BaseDialogFragmentCallback> : androidx.fragment.app.DialogFragment() {

    protected var callback: T? = null

    val dialogTag: String = this.javaClass.name

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    fun setCallback(callback: T): BaseDialogFragment<*> {
        this.callback = callback
        return this
    }

    interface BaseDialogFragmentCallback
}

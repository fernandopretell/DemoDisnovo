package com.limapps.baseui.interfaces

import com.limapps.baseui.views.dialogs.BaseDialogFragment

interface ConfirmationBottomCallback : BaseDialogFragment.BaseDialogFragmentCallback {

    fun onYesOption() {}

    fun onNoOption() {}
}

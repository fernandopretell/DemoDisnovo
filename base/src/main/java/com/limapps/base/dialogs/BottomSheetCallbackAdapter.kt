package com.limapps.base.dialogs

import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.view.View

open class BottomSheetCallbackAdapter : BottomSheetBehavior.BottomSheetCallback() {

    override fun onStateChanged(bottomSheet: View, newState: Int) = Unit

    override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
}
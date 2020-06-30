package com.limapps.base.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.limapps.base.R
import com.limapps.base.activities.BaseFragmentActivity
import com.limapps.base.others.Callback
import com.limapps.base.others.PermissionConstants
import com.limapps.base.utils.ToastUtil
import com.limapps.base.utils.VibratorUtil

abstract class RappiBottomSheet<T : ViewDataBinding> : BottomSheetDialogFragment() {

    protected abstract val binding: T


    val hasLocationPermissionGranted: Boolean
        get() {
            return (activity as BaseFragmentActivity).checkMyLocationPermission()
        }

    val baseActivity: BaseFragmentActivity
        get() {
            return activity as BaseFragmentActivity
        }

    private val bottomSheetBehaviorCallback: BottomSheetBehavior.BottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_HIDDEN -> dismiss()
                BottomSheetBehavior.STATE_EXPANDED -> VibratorUtil.vibrate(context, 10)
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.setContentView(binding.root)

        dialog?.setCanceledOnTouchOutside(false)

        val params = (binding.root.parent as View).layoutParams as androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams

        params.behavior?.let { behavior ->
            if (behavior is BottomSheetBehavior<*>) {
                behavior.setBottomSheetCallback(bottomSheetBehaviorCallback)
                behavior.peekHeight = 5000
                behavior.isHideable = false
            }
        }

        dialog?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                onBackPressedListener()
            } else {
                false
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }


    open fun onBackPressedListener(): Boolean {
        return false
    }

    protected fun showError(message: String) {
        ToastUtil.showErrorAlert(baseActivity, message)
    }

    protected abstract fun toggleLoadingView(isLoading: Boolean)

    fun checkAccessLocationPermission(callback: Callback<PermissionConstants>) {
        (activity as BaseFragmentActivity).checkAccessLocationPermission(callback)
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
}
package com.limapps.baseui.views.dialogs

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.appcompat.app.AppCompatDialog
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import com.limapps.baseui.R
import com.limapps.baseui.utils.getThemeResId
import com.limapps.baseui.views.ViewPagerBottomSheetBehavior


open class ViewPagerBottomSheetDialog : AppCompatDialog {

    private var behavior: ViewPagerBottomSheetBehavior<FrameLayout>? = null

    private var cancelable = true
    private var canceledOnTouchOutside = true
    private var canceledOnTouchOutsideSet: Boolean = false

    private val bottomSheetCallback = object : ViewPagerBottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View,
                                    @ViewPagerBottomSheetBehavior.State newState: Int) {
            if (newState == ViewPagerBottomSheetBehavior.STATE_HIDDEN) {
                cancel()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    @JvmOverloads constructor(context: Context, @StyleRes theme: Int = 0) : super(context, context.getThemeResId(theme)) {
        // We hide the title bar for any style configuration. Otherwise, there will be a gap
        // above the bottom sheet when it is expanded.
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    protected constructor(context: Context, cancelable: Boolean,
                          cancelListener: DialogInterface.OnCancelListener) : super(context, cancelable, cancelListener) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        this.cancelable = cancelable
    }

    override fun setContentView(@LayoutRes layoutResId: Int) {
        super.setContentView(wrapInBottomSheet(layoutResId, null, null))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.let {
            if (Build.VERSION.SDK_INT >= 21) {
                it.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun setContentView(view: View) {
        super.setContentView(wrapInBottomSheet(0, view, null))
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams?) {
        super.setContentView(wrapInBottomSheet(0, view, params))
    }

    override fun setCancelable(cancelable: Boolean) {
        super.setCancelable(cancelable)
        if (this.cancelable != cancelable) {
            this.cancelable = cancelable
            behavior?.isHideable = cancelable
        }
    }

    override fun onStart() {
        super.onStart()
        behavior?.let {
            behavior?.state = ViewPagerBottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun setCanceledOnTouchOutside(cancel: Boolean) {
        super.setCanceledOnTouchOutside(cancel)
        if (cancel && !cancelable) {
            cancelable = true
        }
        canceledOnTouchOutside = cancel
        canceledOnTouchOutsideSet = true
    }

    private fun wrapInBottomSheet(layoutResId: Int, view: View?, params: ViewGroup.LayoutParams?): View {
        var contentView = view
        val container = View.inflate(context,
                R.layout.design_view_pager_bottom_sheet_dialog, null) as FrameLayout
        val coordinator = container.findViewById<androidx.coordinatorlayout.widget.CoordinatorLayout>(R.id.coordinator)
        if (layoutResId != 0 && contentView == null) {
            contentView = layoutInflater.inflate(layoutResId, coordinator, false)
        }
        val bottomSheet = coordinator.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        behavior = ViewPagerBottomSheetBehavior.from(bottomSheet)
        behavior?.setBottomSheetCallback(bottomSheetCallback)
        behavior?.isHideable = cancelable
        if (params == null) {
            bottomSheet.addView(contentView)
        } else {
            bottomSheet.addView(contentView, params)
        }
        // We treat the CoordinatorLayout as outside the dialog though it is technically inside
        coordinator.findViewById<View>(R.id.touch_outside).setOnClickListener {
            if (cancelable && isShowing && shouldWindowCloseOnTouchOutside()) {
                cancel()
            }
        }
        // Handle accessibility events
        ViewCompat.setAccessibilityDelegate(bottomSheet, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View,
                                                           info: AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.isDismissable = cancelable
                if (cancelable) {
                    info.addAction(AccessibilityNodeInfoCompat.ACTION_DISMISS)
                }
            }

            override fun performAccessibilityAction(host: View, action: Int, args: Bundle): Boolean {
                if (action == AccessibilityNodeInfoCompat.ACTION_DISMISS && cancelable) {
                    cancel()
                    return true
                }
                return super.performAccessibilityAction(host, action, args)
            }
        })
        bottomSheet.setOnTouchListener { view, event ->
            // Consume the event and prevent it from falling through
            true
        }
        return container
    }

    internal fun shouldWindowCloseOnTouchOutside(): Boolean {
        if (!canceledOnTouchOutsideSet) {
            val a = context.obtainStyledAttributes(
                    intArrayOf(android.R.attr.windowCloseOnTouchOutside))
            canceledOnTouchOutside = a.getBoolean(0, true)
            a.recycle()
            canceledOnTouchOutsideSet = true
        }
        return canceledOnTouchOutside
    }
}

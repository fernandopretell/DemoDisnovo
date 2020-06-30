package com.limapps.baseui.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import com.limapps.baseui.R
import com.limapps.baseui.views.ViewPagerBottomSheetBehavior

fun androidx.viewpager.widget.ViewPager.setupViewPagerBottomSheet() {
    val bottomSheetParent = findBottomSheetParent(this)
    bottomSheetParent?.let {
        this.addOnPageChangeListener(BottomSheetViewPagerListener(this, bottomSheetParent))
    }
}

private class BottomSheetViewPagerListener constructor(private val viewPager: androidx.viewpager.widget.ViewPager, bottomSheetParent: View) : androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener() {
    private val behavior: ViewPagerBottomSheetBehavior<View> = ViewPagerBottomSheetBehavior.from(bottomSheetParent)

    override fun onPageSelected(position: Int) {
        viewPager.post { behavior.invalidateScrollingChild() }
    }
}

private fun findBottomSheetParent(view: View): View? {
    var current: View? = view
    while (current != null) {
        val params = current.layoutParams
        if (params is androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams && params.behavior is ViewPagerBottomSheetBehavior<*>) {
            return current
        }
        val parent = current.parent
        current = if (parent == null || parent !is View) null else parent
    }
    return null
}

fun Context.getThemeResId(themeId: Int): Int {
    var id = themeId
    if (id == 0) {
        // If the provided theme is 0, then retrieve the dialogTheme from our theme
        val outValue = TypedValue()
        id = if (this.theme.resolveAttribute(
                        R.attr.bottomSheetDialogTheme, outValue, true)) {
            outValue.resourceId
        } else {
            // bottomSheetDialogTheme is not provided; we default to our light theme
            R.style.Theme_Design_Light_BottomSheetDialog
        }
    }
    return id
}

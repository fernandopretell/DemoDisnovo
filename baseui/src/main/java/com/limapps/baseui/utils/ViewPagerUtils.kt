package androidx.core.view

import android.view.View

fun androidx.viewpager.widget.ViewPager.getCurrentView(): View? {
    val currentItem = this.currentItem
    for (i in 0 until this.childCount) {
        val child = this.getChildAt(i)
        val layoutParams = child.layoutParams as androidx.viewpager.widget.ViewPager.LayoutParams
//        if (!layoutParams.isDecor && currentItem == layoutParams.position) {
//            return child
//        }
    }
    return null
}

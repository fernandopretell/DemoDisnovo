package com.limapps.baseui.views

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class AdaptHeightViewPager(
        context: Context,
        attrs: AttributeSet
) : ViewPager(context, attrs) {
    private val unspecifiedSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newHeight = maxChildHeight(widthMeasureSpec)
        val newWidth = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(newWidth, newHeight)
    }

    private fun maxChildHeight(widthMeasureSpec: Int): Int {
            return (0 until childCount)
                    .map {
                        val child = getChildAt(it)
                        child.measure(widthMeasureSpec, unspecifiedSpec)
                        child.measuredHeight
                    }
                    .max() ?: 0
    }
}
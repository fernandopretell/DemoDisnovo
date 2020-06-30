package com.limapps.baseui.views.loaders

import android.graphics.Paint

internal interface LoaderView {
    fun setRectColor(rectPaint: Paint)

    fun invalidate()

    fun valueSet(): Boolean

    fun shouldAnimate(): Boolean = false

    fun setShouldAnimate(shouldAnimate: Boolean) {}
}
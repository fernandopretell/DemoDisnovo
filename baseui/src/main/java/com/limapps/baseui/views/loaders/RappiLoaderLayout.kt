package com.limapps.baseui.views.loaders

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import com.limapps.baseui.R

class RappiLoaderLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    init {
        val size = resources.getDimensionPixelSize(R.dimen.spacing_xxhuge)
        setBackgroundColor(ContextCompat.getColor(context, R.color.white_translucent))
        addView(RappiLoaderView(context), LayoutParams(size, size).apply {
            gravity = Gravity.CENTER
        })
    }
}
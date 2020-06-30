package com.limapps.baseui.views.loaders

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.airbnb.lottie.LottieAnimationView
import com.limapps.baseui.R

class RappiLoaderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : FrameLayout(context, attrs, defStyle) {

    private val loader: LottieAnimationView by lazy { findViewById<LottieAnimationView>(R.id.lottieAnimationView) }

    init {
        View.inflate(context, R.layout.layout_loader, this)
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        this.loader.visibility = visibility
    }

}
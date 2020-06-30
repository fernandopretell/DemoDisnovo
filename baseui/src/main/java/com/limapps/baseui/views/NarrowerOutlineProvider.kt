package com.limapps.baseui.views

import android.graphics.Outline
import android.os.Build
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import android.view.View
import android.view.ViewOutlineProvider

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NarrowerOutlineProvider(
        @param:Px private val insetHorizontal: Int = 0,
        @param:Px private val insetTop: Int = 0,
        @param:Px private val insetBottom: Int = 0,
        @param:Px private val radius: Int = 0,
        private val alpha: Float
) : ViewOutlineProvider() {

    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(
                insetHorizontal,
                insetTop,
                view.width - insetHorizontal,
                view.height - insetBottom,
                radius.toFloat()
        )
        outline.alpha = alpha

    }
}
package com.limapps.baseui.utils

import androidx.cardview.widget.CardView
import com.limapps.baseui.R
import com.limapps.baseui.views.NarrowerOutlineProvider

fun CardView.configureOutline(
        insetBottom: Int = context.resources.getDimensionPixelOffset(R.dimen.home_widget_generic_vertical_shadow_offset),
        radius: Int = context.resources.getDimensionPixelOffset(R.dimen.corner_stores)
) {
    val shadowAlpha = .4f

    clipToOutline = true
    clipChildren = true

    outlineProvider = NarrowerOutlineProvider(
            insetBottom = insetBottom,
            radius = radius,
            alpha = shadowAlpha
    )
}

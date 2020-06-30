package com.limapps.baseui.views.tooltip

import android.content.Context
import androidx.core.content.ContextCompat
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.view.View
import com.limapps.baseui.R
import com.limapps.baseui.rappitooltip.Tooltip
import com.limapps.common.toSpannable

class TooltipUtil {

    companion object {

        fun getTitleMessage(title: String, context: Context): SpannableStringBuilder {
            return SpannableStringBuilder(title.toSpannable(true, ContextCompat.getColor(context, R.color.white)))
        }
    }

}

fun View.buildRappiTooltip(
        text: SpannableString,
        callback: Tooltip.Callback? = null,
        gravity: Tooltip.Gravity = Tooltip.Gravity.TOP,
        animation: Tooltip.AnimationBuilder? = Tooltip.AnimationBuilder.DEFAULT,
        style: Int = R.style.ToolTipLayoutDefaultStyle,
        withOverlay: Boolean = false
): Tooltip.TooltipView {
    return Tooltip.make(this.context,
            Tooltip.Builder()
                    .anchor(this, gravity)
                    .closePolicy(Tooltip.ClosePolicy()
                            .insidePolicy(true, true)
                            .outsidePolicy(true, false), 0)
                    .spannable(text)
                    .withArrow(true)
                    .withOverlay(withOverlay)
                    .withCallback(callback)
                    .floatingAnimation(animation)
                    .withStyleId(style)
                    .build()
    )
}


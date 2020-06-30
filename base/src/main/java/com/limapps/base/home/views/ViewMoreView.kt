package com.limapps.base.home.views

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.limapps.common.hide
import com.limapps.common.show
import com.limapps.base.R

class ViewMoreView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val caption by lazy { findViewById<TextView>(R.id.textView_caption) }

    init {
        inflate(context, R.layout.view_view_more, this)
        setBackgroundColor(ContextCompat.getColor(context, R.color.white_translucent))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutParams.apply {
            height = resources.getDimensionPixelSize(R.dimen.spacing_xxhuge)
            width = LayoutParams.MATCH_PARENT
        }
    }

    fun bind(elementsCount: Int) {
        if (elementsCount == 0) {
            hide()
        } else {
            show()
            val elementsCountString = elementsCount.toString()
            val formattedString = SpannableString(context.getString(R.string.home_view_x_more, elementsCountString))
            val start = formattedString.indexOf(elementsCountString)
            val end = formattedString.indexOf(" ", start)
            formattedString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    start,
                    end,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
            )
            caption.text = formattedString
        }
    }

    fun basicBind() {
        show()
        caption.text = context.getString(R.string.home_view_more)
    }
}

package com.limapps.base.views

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.limapps.base.R
import com.limapps.base.adapters.GenericAdapterRecyclerView
import com.limapps.base.adapters.GenericAdapterRecyclerView.ItemAnimationView
import com.limapps.common.show
import com.limapps.baseui.others.OnClickViewListener
import com.limapps.baseui.animations.LONG_DURATION
import com.limapps.baseui.animations.SIZE_STANDARD
import com.limapps.baseui.animations.appearFromBottomAndFadeIn

open class TitleItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
        private var withAnimation: Boolean = false, private var animationSpeed: Int = LONG_DURATION
) : TextView(context, attrs, defStyleAttr), ItemAnimationView<TextBuilder> {

    private lateinit var textBuilder: TextBuilder
    private var clicked: Boolean = false

    init {
        val marginLayoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams = marginLayoutParams
        gravity = Gravity.CENTER_HORIZONTAL
    }

    override fun bind(textBuilder: TextBuilder, position: Int) {
        this.textBuilder = textBuilder
        val text = textBuilder.text
        setText(text)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(textBuilder.textSize).toFloat())
        setTextColor(ContextCompat.getColor(context, textBuilder.textColor))
        gravity = textBuilder.gravity

        setMargins()
        setPadding()

        clicked = textBuilder.isUnderline || textBuilder.clickId != 0

        paintFlags = (paintFlags or Paint.UNDERLINE_TEXT_FLAG).takeIf { textBuilder.isUnderline } ?: paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()

        if (textBuilder.font == null) {
            typeface = ResourcesCompat.getFont(context, R.font.default_font_bold.takeIf { textBuilder.isBold }
                    ?: R.font.default_font_regular)
        } else {
            textBuilder.font?.let {
                typeface = Typeface.create(ResourcesCompat.getFont(context, it), Typeface.BOLD.takeIf { textBuilder.isBold }
                        ?: Typeface.NORMAL)
            }
        }

        setCompoundDrawablesWithIntrinsicBounds(textBuilder.drawableLeft, 0, textBuilder.drawableRight, 0)

        if (textBuilder.background != 0) {
            setBackgroundResource(textBuilder.background)
        } else {
            background = null
        }
        setAllCaps(textBuilder.isAllCaps)
    }

    private fun setPadding() {
        setPadding(resources.getDimensionPixelSize(textBuilder.paddingLeft),
                resources.getDimensionPixelSize(textBuilder.paddingTop),
                resources.getDimensionPixelSize(textBuilder.paddingRight),
                resources.getDimensionPixelSize(textBuilder.paddingBottom))

        compoundDrawablePadding = resources.getDimensionPixelSize(textBuilder.paddingDrawable)
    }

    private fun setMargins() {
        val marginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.topMargin = resources.getDimensionPixelSize(textBuilder.marginTop)
        marginLayoutParams.bottomMargin = resources.getDimensionPixelSize(textBuilder.marginBottom)
        marginLayoutParams.leftMargin = resources.getDimensionPixelSize(textBuilder.marginLeft)
        marginLayoutParams.rightMargin = resources.getDimensionPixelSize(textBuilder.marginRight)
        layoutParams = marginLayoutParams
    }

    override fun setItemClickListener(onItemClickListener: GenericAdapterRecyclerView.OnItemClickListener?) {
        setOnClickListener(object : OnClickViewListener() {
            override fun onClickView(view: View) {
                if (clicked) {
                    onItemClickListener?.onItemClicked(this@TitleItemView)
                }
            }
        })
    }

    override fun getIdForClick(): Int {
        val clickId = textBuilder.clickId
        return clickId.takeIf { clickId != 0 } ?: TITLE_VIEW_ID
    }

    override fun initAnimation() {
        if (withAnimation) {
            appearFromBottomAndFadeIn(animationSpeed, SIZE_STANDARD.toFloat())
        }
    }

    override fun finishAnimation() = show()

    override fun shouldAnimate(): Boolean = withAnimation

    override fun getData() = textBuilder

    companion object {
        val TITLE_VIEW_ID = 0x10
    }
}
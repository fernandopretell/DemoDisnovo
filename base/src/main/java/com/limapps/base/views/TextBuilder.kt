package com.limapps.base.views

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import android.text.SpannableString
import android.view.Gravity

import com.limapps.base.R
import com.limapps.base.others.ResourcesProvider

open class TextBuilder {

    var text: CharSequence = ""
    var textSize = R.dimen.font_large
    var textColor = R.color.text_color_primary
    var textColorHint = R.color.text_color_secondary
    var gravity = Gravity.CENTER
    var marginTop = R.dimen.spacing_large
    var marginBottom = R.dimen.spacing_large
    var marginLeft = R.dimen.spacing_large
    var marginRight = R.dimen.spacing_large
    var paddingTop = R.dimen.spacing_empty
    var paddingBottom = R.dimen.spacing_empty
    var paddingLeft = R.dimen.spacing_empty
    var paddingRight = R.dimen.spacing_empty
    var paddingDrawable = R.dimen.spacing_empty
    var isAllCaps: Boolean = false
    var isUnderline: Boolean = false
    var isBold = false
    var clickId: Int = 0
    var backgroundColor = android.R.color.transparent
    var widthWeightLoader: Float = 0.toFloat()
    var heightWeightLoader: Float = 0.toFloat()
    var hint: Int = 0
    var background: Int = 0
    var drawableLeft: Int = 0
    var drawableRight: Int = 0
    var data: Any? = null
    var font: Int? = null

    override fun equals(obj: Any?): Boolean {
        val textItem = obj as? TextBuilder
        return textItem?.let { (it.text == text) } ?: run { false }
    }

    companion object {
        const val CLICKABLE_ID = 0x10
    }

    fun setText(@StringRes text: Int) = apply { this.text = ResourcesProvider.getString(text) }

    fun setQuantityText(isTagRequired: Boolean, @StringRes text: Int, amount: Int, @StringRes textWithTag: Int, tag: String) = apply {
        this.text = if (!isTagRequired || tag.isEmpty()) {
            ResourcesProvider.getQuantityString(text, amount, amount)
        } else {
            ResourcesProvider.getQuantityString(textWithTag, amount, amount, tag)
        }
    }

    fun setData(data: Any?) = apply { this.data = data }

    fun setText(text: String) = apply { this.text = text }
    fun setText(text: SpannableString) = apply { this.text = text }
    fun setHint(@StringRes hint: Int) = apply { this.hint = hint }

    fun setColor(@ColorRes color: Int) = apply { this.textColor = color }

    fun setBackground(@DrawableRes @ColorRes background: Int) = apply { this.background = background }

    fun setGravity(gravity: Int) = apply { this.gravity = gravity }
    fun setClickId(clickId: Int) = apply { this.clickId = clickId }

    fun setWidthWeightLoader(widthWeightLoader: Float) = apply { this.widthWeightLoader = widthWeightLoader }
    fun setHeightWeightLoader(heightWeightLoader: Float) = apply { this.heightWeightLoader = heightWeightLoader }

    fun setMargin(@DimenRes margin: Int) = apply {
        this.marginBottom = margin
        this.marginRight = margin
        this.marginLeft = margin
        this.marginTop = margin
    }

    fun setMarginBottom(@DimenRes marginBottom: Int) = apply { this.marginBottom = marginBottom }
    fun setMarginRight(@DimenRes marginRight: Int) = apply { this.marginRight = marginRight }
    fun setMarginLeft(@DimenRes marginLeft: Int) = apply { this.marginLeft = marginLeft }
    fun setMarginTop(@DimenRes marginTop: Int) = apply { this.marginTop = marginTop }

    fun setPaddingBottom(@DimenRes paddingBottom: Int) = apply { this.paddingBottom = paddingBottom }
    fun setPaddingRight(@DimenRes paddingRight: Int) = apply { this.paddingRight = paddingRight }
    fun setPaddingLeft(@DimenRes paddingLeft: Int) = apply { this.paddingLeft = paddingLeft }
    fun setPaddingTop(@DimenRes paddingTop: Int) = apply { this.paddingTop = paddingTop }
    fun setPaddingDrawable(@DimenRes paddingDrawable: Int) = apply { this.paddingDrawable = paddingDrawable }

    fun setSize(@DimenRes size: Int) = apply { this.textSize = size }

    fun setDrawableLeft(@DrawableRes drawableLeft: Int) = apply { this.drawableLeft = drawableLeft }
    fun setDrawableRight(@DrawableRes drawableRight: Int) = apply { this.drawableRight = drawableRight }

    fun setBold(bold: Boolean) = apply { this.isBold = bold }
    fun setAllCaps(allCaps: Boolean) = apply { this.isAllCaps = allCaps }
    fun setUnderline(underline: Boolean) = apply { this.isUnderline = underline }
    fun setFont(font: Int) = apply { this.font = font }
    fun setTextSize(@DimenRes textSize: Int) = apply { this.textSize = textSize }
}
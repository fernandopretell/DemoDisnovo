package com.limapps.base.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.TypedValue

fun getStatusBarHeight(context: Context): Int {
    var result = 0
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun resolveThemeDrawable(themeDrawableAttr: Int, themedContext: Context): Drawable? {
    val attrs = intArrayOf(themeDrawableAttr)
    val array = themedContext.obtainStyledAttributes(attrs)
    val drawable = array.getDrawable(0)
    array.recycle()
    return drawable
}

fun getActionBarSizePixels(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    val value = TypedValue()
    context.theme.resolveAttribute(android.R.attr.actionBarSize, value, true)
    return TypedValue.complexToDimensionPixelSize(value.data, displayMetrics)
}

fun textAsBitmap(text: String, textSize: Float, textColor: Int): Bitmap {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.textSize = textSize
    paint.color = textColor
    paint.textAlign = Paint.Align.LEFT
    val baseline = -paint.ascent()
    val width = (paint.measureText(text) + 0.0f).toInt()
    val height = (baseline + paint.descent() + 0.0f).toInt()
    val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(image)
    canvas.drawText(text, 0f, baseline, paint)
    return image
}
package com.limapps.baseui.views


import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.limapps.baseui.R

abstract class ProgressableView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : View(context, attrs, defStyle) {

    protected val defaultProgress = 0f
    protected val defaultStrokeWidth = 5f
    protected val defaultBackgroundColor = context.resources.getColor(R.color.gray)
    protected val defaultForegroundColor = context.resources.getColor(R.color.yellow)
    protected var animationDuration = 700L

    protected var strokeWidth = defaultStrokeWidth
    protected var nonProgressColor = defaultBackgroundColor
    protected var progressColor = defaultForegroundColor
    protected val backgroundPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    protected val foregroundPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    var progress = defaultProgress
        set(value) {
            field = value
            invalidate()
        }

    var minValue = 0
        set(value) {
            field = value
            invalidate()
        }

    var maxValue = 100
        set(value) {
            field = value
            invalidate()
        }

    init {
        attrs?.let {
            val typedArray = context.theme.obtainStyledAttributes(it, R.styleable.ProgressableView, 0, 0)

            try {
                strokeWidth = typedArray.getDimensionPixelSize(R.styleable.ProgressableView_strokeWidth, defaultStrokeWidth.toInt()).toFloat()
                minValue = typedArray.getInteger(R.styleable.ProgressableView_minValue, 0)
                maxValue = typedArray.getInteger(R.styleable.ProgressableView_maxValue, 100)
                nonProgressColor = typedArray.getColor(R.styleable.ProgressableView_backgroundColor, defaultBackgroundColor)
                progressColor = typedArray.getColor(R.styleable.ProgressableView_progressColor, defaultForegroundColor)
                progress = typedArray.getFloat(R.styleable.ProgressableView_progress, defaultProgress)
            } finally {
                typedArray.recycle()
            }
        }

        backgroundPaint.apply {
            color = nonProgressColor
            style = Paint.Style.STROKE
            strokeWidth = this@ProgressableView.strokeWidth
            strokeCap = Paint.Cap.ROUND
        }

        foregroundPaint.apply {
            color = progressColor
            style = Paint.Style.STROKE
            strokeWidth = this@ProgressableView.strokeWidth
            strokeCap = Paint.Cap.ROUND
        }
    }

}
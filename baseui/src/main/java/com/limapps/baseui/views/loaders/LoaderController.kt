package com.limapps.baseui.views.loaders

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.*
import android.view.animation.LinearInterpolator

internal class LoaderController(private val loaderView: LoaderView) {

    companion object {

        private const val MAX_COLOR_CONSTANT_VALUE = 255
        private const val ANIMATION_CYCLE_DURATION = 750
        const val MIN_WEIGHT = 0.0f
        const val MAX_WEIGHT = 1.0f
        const val USE_GRADIENT_DEFAULT = false
        val COLOR_DEFAULT_GRADIENT = Color.rgb(245, 245, 245)
        val COLOR_DEFAULT_GREY = Color.rgb(215, 215, 215)
        val COLOR_DARKER_GREY = Color.rgb(180, 180, 180)
    }

    private var linearGradient: LinearGradient? = null
    private var progress = 0f
    private var valueAnimator: ValueAnimator? = null
    private var widthWeight = MAX_WEIGHT
    private var heightWeight = MAX_WEIGHT
    private var useGradient = USE_GRADIENT_DEFAULT

    private val rectPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    }

    init {
        initialize()
    }

    private fun initialize() {
        loaderView.setRectColor(rectPaint)
        setValueAnimator(0.5f, 1f, ObjectAnimator.INFINITE)
    }

    fun onDraw(canvas: Canvas) {
        val width = canvas.width
        val height = canvas.height

        val marginHeight = height * (1 - heightWeight) / 2
        rectPaint.alpha = (progress * MAX_COLOR_CONSTANT_VALUE).toInt()

        if (useGradient) {
            prepareGradient(width * widthWeight)
        }

        if (loaderView is LoaderCircleImageView) {
            val circleSize = (width / 2).toFloat()
            canvas.drawCircle(circleSize, circleSize, circleSize, rectPaint)
        } else {
            canvas.drawRect(0f, marginHeight, width * widthWeight, height - marginHeight, rectPaint)
        }
    }

    fun onSizeChanged() {
        linearGradient = null
        if (loaderView.shouldAnimate()) {
            startLoading()
        } else {
            stopLoading()
        }
    }

    private fun prepareGradient(width: Float) {
        if (linearGradient == null) {
            linearGradient = LinearGradient(0f, 0f, width, 0f, rectPaint.color,
                    COLOR_DEFAULT_GRADIENT, Shader.TileMode.MIRROR)
        }
        rectPaint.shader = linearGradient
    }

    fun startLoading() {
        if (!loaderView.valueSet()) {
            valueAnimator?.cancel()
            initialize()
            valueAnimator?.start()
        }
    }

    fun setHeightWeight(heightWeight: Float) {
        this.heightWeight = validateWeight(heightWeight)
    }

    fun setWidthWeight(widthWeight: Float) {
        this.widthWeight = validateWeight(widthWeight)
    }

    fun setUseGradient(useGradient: Boolean) {
        this.useGradient = useGradient
    }

    private fun validateWeight(weight: Float): Float = when {
        weight > MAX_WEIGHT -> MAX_WEIGHT
        weight < MIN_WEIGHT -> MIN_WEIGHT
        else -> weight
    }

    fun stopLoading() {
        valueAnimator?.let {
            it.cancel()
            it.removeAllUpdateListeners()
        }
        progress = 0f
    }

    private fun setValueAnimator(begin: Float, end: Float, repeatCount: Int) {
        valueAnimator = ValueAnimator.ofFloat(begin, end)
        valueAnimator?.let {
            it.repeatCount = repeatCount
            it.duration = ANIMATION_CYCLE_DURATION.toLong()
            it.repeatMode = ValueAnimator.REVERSE
            it.interpolator = LinearInterpolator()
            it.addUpdateListener { animation ->
                progress = animation.animatedValue as Float
                loaderView.invalidate()
            }
        }
    }
}

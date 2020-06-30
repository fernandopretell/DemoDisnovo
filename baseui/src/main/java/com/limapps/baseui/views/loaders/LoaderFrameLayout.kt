package com.limapps.baseui.views.loaders

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.Shader
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import com.limapps.baseui.R
import com.limapps.common.isMarshmallow

open class LoaderFrameLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : FrameLayout(context, attrs, defStyle) {

//    private var maskOffsetX: Int = 0
//    private var maskRect: Rect? = null
//    private val maskPaint: Paint
//    private var maskAnimator: ValueAnimator? = null
//
//    private var localAvailableBitmap: Bitmap? = null
//    private var localMaskBitmap: Bitmap? = null
//    private var destinationBitmap: Bitmap? = null
//    private var sourceMaskBitmap: Bitmap? = null
//    private var canvasForRendering: Canvas? = null
//
//    private var isAnimationStarted: Boolean = false
//    private var autoStart: Boolean = false
//    private var shimmerAnimationDuration: Int = 600
//    private var shimmerColor: Int = 0
//    private var shimmerAngle: Int = 0
//
//    private var startAnimationGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null
//
//    private val shimmerAnimation: Animator
//        get() {
//            maskAnimator?.let {
//                return maskAnimator as ValueAnimator
//            }
//
//            if (maskRect == null) {
//                maskRect = calculateMaskRect()
//            }
//
//            val animationToX = width
//            val animationFromX = -animationToX
//            val shimmerBitmapWidth = maskRect?.width() ?: 0
//            val shimmerAnimationFullLength = animationToX - animationFromX
//
//            maskAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
//                    .apply {
//                        duration = shimmerAnimationDuration.toLong()
//                        repeatCount = ObjectAnimator.INFINITE
//                        interpolator = AccelerateDecelerateInterpolator()
//                    }
//
//            val value = FloatArray(1)
//
//            maskAnimator?.addUpdateListener { animation ->
//                value[0] = animation.animatedValue as Float
//                maskOffsetX = (animationFromX + shimmerAnimationFullLength * value.first()).toInt()
//                if (maskOffsetX + shimmerBitmapWidth >= 0) {
//                    invalidate()
//                }
//            }
//
//            return maskAnimator as ValueAnimator
//        }
//
//    init {
//
//        setWillNotDraw(false)
//
//        maskPaint = Paint().apply {
//            isAntiAlias = true
//            isDither = true
//            isFilterBitmap = true
//            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
//        }
//
//        val a = context.theme.obtainStyledAttributes(
//                attrs,
//                R.styleable.LoaderFrameLayout,
//                0, 0)
//
//        try {
//            shimmerAngle = a.getInteger(R.styleable.LoaderFrameLayout_shimmer_angle, DEFAULT_ANGLE)
//            shimmerAnimationDuration = a.getInteger(R.styleable.LoaderFrameLayout_shimmer_animation_duration, DEFAULT_ANIMATION_DURATION)
//            shimmerColor = a.getColor(R.styleable.LoaderFrameLayout_shimmer_color, getColor(R.color.shimmer_color))
//            autoStart = a.getBoolean(R.styleable.LoaderFrameLayout_shimmer_auto_start, false)
//        } finally {
//            a.recycle()
//        }
//        setShimmerAngle(shimmerAngle)
//        if (autoStart && visibility == View.VISIBLE) {
//            startShimmerAnimation()
//        }
//    }
//
//    override fun onDetachedFromWindow() {
//        resetShimmering()
//        super.onDetachedFromWindow()
//    }
//
//    override fun dispatchDraw(canvas: Canvas) {
//        if (!isAnimationStarted || width <= 0 || height <= 0) {
//            super.dispatchDraw(canvas)
//        } else {
//            dispatchDrawUsingBitmap(canvas)
//        }
//    }
//
//    override fun setVisibility(visibility: Int) {
//        super.setVisibility(visibility)
//        if (visibility == View.VISIBLE) {
//            if (autoStart) {
//                startShimmerAnimation()
//            }
//        } else {
//            stopShimmerAnimation()
//        }
//    }
//
//    fun startShimmerAnimation() {
//        if (isAnimationStarted) {
//            return
//        }
//
//        if (width == 0) {
//            startAnimationGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
//                override fun onGlobalLayout() {
//                    removeGlobalLayoutListener(this)
//                    startShimmerAnimation()
//                }
//            }
//
//            viewTreeObserver.addOnGlobalLayoutListener(startAnimationGlobalLayoutListener)
//
//            return
//        }
//
//        val animator = shimmerAnimation
//        animator.start()
//        isAnimationStarted = true
//    }
//
//    fun stopShimmerAnimation() {
//        startAnimationGlobalLayoutListener?.let {
//            removeGlobalLayoutListener(it)
//        }
//
//        resetShimmering()
//    }
//
//    fun setShimmerColor(shimmerColor: Int) {
//        this.shimmerColor = shimmerColor
//        resetIfStarted()
//    }
//
//    fun setShimmerAnimationDuration(durationMillis: Int) {
//        this.shimmerAnimationDuration = durationMillis
//        resetIfStarted()
//    }
//
//    fun setShimmerAngle(angle: Int) {
//        if (angle < MIN_ANGLE_VALUE || MAX_ANGLE_VALUE < angle) {
//            throw IllegalArgumentException(String.format("shimmerAngle value must be between %d and %d",
//                    MIN_ANGLE_VALUE,
//                    MAX_ANGLE_VALUE))
//        }
//        this.shimmerAngle = angle
//        resetIfStarted()
//    }
//
//    private fun resetIfStarted() {
//        if (isAnimationStarted) {
//            resetShimmering()
//            startShimmerAnimation()
//        }
//    }
//
//    private fun dispatchDrawUsingBitmap(canvas: Canvas) {
//        super.dispatchDraw(canvas)
//
//        localAvailableBitmap = getDestinationBitmap()
//        if (localAvailableBitmap == null) {
//            return
//        }
//
//        if (canvasForRendering == null) {
//            canvasForRendering = Canvas(localAvailableBitmap)
//        }
//
//        canvasForRendering?.let {
//            drawMask(it)
//        }
//
//        maskRect?.let {
//            canvas.save()
//            canvas.clipRect(maskOffsetX, 0, maskOffsetX + it.width(), height)
//            canvas.drawBitmap(localAvailableBitmap, 0F, 0F, null)
//            canvas.restore()
//        }
//
//        localAvailableBitmap = null
//    }
//
//    private fun drawMask(renderCanvas: Canvas) {
//        localMaskBitmap = getSourceMaskBitmap()
//        if (localMaskBitmap == null) {
//            return
//        }
//
//        localMaskBitmap?.let {
//            renderCanvas.save()
//            renderCanvas.clipRect(maskOffsetX, 0,
//                    maskOffsetX + it.width,
//                    height)
//
//            super.dispatchDraw(renderCanvas)
//            renderCanvas.drawBitmap(localMaskBitmap, maskOffsetX.toFloat(), 0F, maskPaint)
//
//            renderCanvas.restore()
//        }
//
//        localMaskBitmap = null
//    }
//
//    private fun resetShimmering() {
//        maskAnimator?.let {
//            it.end()
//            it.removeAllUpdateListeners()
//        }
//
//        maskAnimator = null
//        isAnimationStarted = false
//
//        releaseBitMaps()
//    }
//
//    private fun releaseBitMaps() {
//        sourceMaskBitmap?.let {
//            it.recycle()
//            sourceMaskBitmap = null
//        }
//
//        destinationBitmap?.let {
//            it.recycle()
//            destinationBitmap = null
//        }
//
//        canvasForRendering = null
//    }
//
//    private fun getDestinationBitmap(): Bitmap {
//        if (destinationBitmap == null) {
//            destinationBitmap = createBitmap(width, height)
//        }
//
//        return destinationBitmap as Bitmap
//    }
//
//    private fun getSourceMaskBitmap(): Bitmap? {
//        sourceMaskBitmap?.let {
//            return sourceMaskBitmap
//        }
//
//        maskRect?.let {
//            val width = it.width()
//            val height = height
//
//            val edgeColor = reduceColorAlphaValueToZero(shimmerColor)
//            val gradient = LinearGradient(
//                    -it.left.toFloat(), 0F,
//                    width.toFloat() + it.left.toFloat(), 0F,
//                    intArrayOf(edgeColor, shimmerColor, shimmerColor, edgeColor),
//                    floatArrayOf(0.25f, 0.47f, 0.53f, 0.75f),
//                    Shader.TileMode.CLAMP)
//            val paint = Paint()
//            paint.shader = gradient
//
//            sourceMaskBitmap = createBitmap(width, height)
//            val canvas = Canvas(sourceMaskBitmap)
//            canvas.rotate(shimmerAngle.toFloat(), width / 2F, height / 2F)
//            canvas.drawRect(it.left.toFloat(), it.top.toFloat(), width.toFloat() + it.left, it.bottom.toFloat(), paint)
//        }
//
//        return sourceMaskBitmap
//    }
//
//    private fun createBitmap(width: Int, height: Int): Bitmap {
//        return try {
//            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        } catch (e: OutOfMemoryError) {
//            System.gc()
//            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        }
//    }
//
//    private fun getColor(id: Int): Int {
//        return if (isMarshmallow()) {
//            context.getColor(id)
//        } else {
//            ContextCompat.getColor(context, id)
//        }
//    }
//
//    private fun removeGlobalLayoutListener(listener: ViewTreeObserver.OnGlobalLayoutListener) {
//        viewTreeObserver.removeOnGlobalLayoutListener(listener)
//    }
//
//    private fun reduceColorAlphaValueToZero(actualColor: Int): Int {
//        return Color.argb(0, Color.red(actualColor), Color.green(actualColor), Color.blue(actualColor))
//    }
//
//    private fun calculateMaskRect(): Rect {
//        val shimmerWidth = width / 2
//        if (shimmerAngle == 0) {
//            return Rect((shimmerWidth * 0.25).toInt(), 0, (shimmerWidth * 0.75).toInt(), height)
//        }
//
//        val top = 0
//        val center = (height * 0.5).toInt()
//        val right = (shimmerWidth * 0.75).toInt()
//        val originalTopRight = Point(right, top)
//        val originalCenterRight = Point(right, center)
//
//        val rotatedTopRight = rotatePoint(originalTopRight, shimmerAngle.toFloat(),
//                (shimmerWidth / 2).toFloat(), (height / 2).toFloat())
//        val rotatedCenterRight = rotatePoint(originalCenterRight, shimmerAngle.toFloat(),
//                (shimmerWidth / 2).toFloat(), (height / 2).toFloat())
//        val rotatedIntersection = getTopIntersection(rotatedTopRight, rotatedCenterRight)
//        val halfMaskHeight = distanceBetween(rotatedCenterRight, rotatedIntersection)
//
//        val paddingVertical = height / 2 - halfMaskHeight
//        val paddingHorizontal = shimmerWidth - rotatedIntersection.x
//
//        return Rect(paddingHorizontal, paddingVertical, shimmerWidth - paddingHorizontal, height - paddingVertical)
//    }
//
//    private fun getTopIntersection(firstPoint: Point, secondPoint: Point): Point {
//        val x1 = firstPoint.x
//        val x2 = secondPoint.x
//        val y1 = -firstPoint.y
//        val y2 = -secondPoint.y
//        // slope-intercept form of the line represented by the two points
//        val m = (y2 - y1) / (x2 - x1)
//        val b = y1 - m * x1
//        // The intersection with the line represented by the top of the canvas
//        val x = ((0 - b) / m)
//        return Point(x, 0)
//    }
//
//    private fun rotatePoint(point: Point, degrees: Float, cx: Float, cy: Float): Point {
//        val points = FloatArray(2)
//        points[0] = point.x.toFloat()
//        points[1] = point.y.toFloat()
//
//        val transform = Matrix()
//        transform.setRotate(degrees, cx, cy)
//        transform.mapPoints(points)
//
//        return Point(points[0].toInt(), points[1].toInt())
//    }
//
//    private fun distanceBetween(p1: Point, p2: Point): Int {
//        return Math.ceil(Math.sqrt(Math.pow(p1.x.toDouble() - p2.x, 2.0) + Math.pow(p1.y.toDouble() - p2.y, 2.0))).toInt()
//    }
//
//    companion object {
//
//        private val DEFAULT_ANIMATION_DURATION = 1000
//        private val DEFAULT_ANGLE = 20
//        private val MIN_ANGLE_VALUE = 0
//        private val MAX_ANGLE_VALUE = 30
//    }
}
package com.limapps.baseui.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.Nullable
import kotlin.properties.Delegates
import com.limapps.baseui.R

class ZoomableView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 2
        const val CLICK = 3
        const val MAX_SCALE = 10
        const val STARTING_SCALE = 2f
        const val MIN_SCALE = 1f
    }

    var mode = NONE
    var newMatrix = Matrix()
    var last = PointF()
    var start = PointF()
    var minScale = MIN_SCALE
    var maxScale = 2f
    var arrayMatrix by Delegates.notNull<FloatArray>()

    var redundantXSpace: Float = 0f
    var redundantYSpace: Float = 0f
    var width: Float = 0f
    var height: Float = 0f
    var saveScale = MIN_SCALE
    var viewRight: Float = 0f
    var viewBottom: Float = 0f
    var origWidth: Float = 0f
    var origHeight: Float = 0f
    var bitmapWidth: Float = 0f
    var bitmapHeight: Float = 0f

    var isTouching = false

    var originalImageViewWidth: Float = 0f
    var originalImageViewHeight: Float = 0f

    var scaleDetector by Delegates.notNull<ScaleGestureDetector>()
    var isFullScreen: Boolean = false

    var canGoFullScreen = false
    var changeToNormalScreen = false
    var isCreating = true

    var dragFullScreen = false
    var doubleTapFullScreen = false
    var canScale = false
    var callFullScreen = false

    var isFitStyle = false

    var gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            handleTaps()
            return super.onDoubleTap(e)
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            handleTaps()
            return super.onSingleTapUp(e)
        }
    })

    init {
        if (attrs != null) {
            applyAttributes(context, attrs)
        }
        scaleDetector = ScaleGestureDetector(context, ScaleListener())
        newMatrix.setTranslate(MIN_SCALE, MIN_SCALE)
        arrayMatrix = FloatArray(9)
        imageMatrix = newMatrix
        scaleType = ScaleType.MATRIX

        setOnTouchListener { _, event ->
            if (canScale) {
                scaleDetector.onTouchEvent(event)
            }
            if (doubleTapFullScreen) {
                gestureDetector.onTouchEvent(event)
            }

            newMatrix.getValues(arrayMatrix)
            val x = arrayMatrix[Matrix.MTRANS_X]
            val y = arrayMatrix[Matrix.MTRANS_Y]
            val curr = PointF(event.x, event.y)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    last.set(event.x, event.y)
                    start.set(last)
                    mode = DRAG
                    isTouching = true
                }

                MotionEvent.ACTION_POINTER_DOWN -> {
                    last.set(event.x, event.y)
                    start.set(last)
                    mode = ZOOM
                    isTouching = true
                }
                MotionEvent.ACTION_MOVE -> {
                    isTouching = true

                    if (mode == ZOOM || mode == DRAG && saveScale > minScale) {
                        var deltaX: Float = curr.x - last.x
                        var deltaY: Float = curr.y - last.y
                        val scaleWidth = Math.round(origWidth * saveScale).toFloat()
                        val scaleHeight = Math.round(origHeight * saveScale).toFloat()
                        if (scaleWidth < width) {
                            deltaX = 0f

                            if (y + deltaY > 0) {
                                deltaY = -y
                            } else if (y + deltaY < -viewBottom) {
                                deltaY = -(y + viewBottom)
                            }
                        } else if (scaleHeight < height) {
                            deltaY = 0f
                            if (x + deltaX > 0) {
                                deltaX = -x
                            } else if (x + deltaX < -viewRight) {
                                deltaX = -(x + viewRight)
                            }
                        } else {
                            if (x + deltaX > 0) {
                                deltaX = -x
                            } else if (x + deltaX < -viewRight) {
                                deltaX = -(x + viewRight)
                            }
                            if (y + deltaY > 0) {
                                deltaY = -y
                            } else if (y + deltaY < -viewBottom) {
                                deltaY = -(y + viewBottom)
                            }
                        }
                        newMatrix.postTranslate(deltaX, deltaY)
                        last.set(curr.x, curr.y)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    isTouching = false
                    mode = NONE
                    val xDiff = Math.abs(curr.x - start.x).toInt()
                    val yDiff = Math.abs(curr.y - start.y).toInt()
                    if (xDiff < CLICK && yDiff < CLICK) {
                        performClick()
                    }
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    isTouching = false
                    mode = NONE
                }
            }
            imageMatrix = newMatrix
            invalidate()
            true
        }
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        bitmapWidth = bm.width.toFloat()
        bitmapHeight = bm.height.toFloat()
    }

    override fun setImageDrawable(@Nullable drawable: Drawable?) {
        super.setImageDrawable(drawable)
        drawable?.let {
            bitmapWidth = drawable.intrinsicWidth.toFloat()
            bitmapHeight = drawable.intrinsicHeight.toFloat()
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mode = ZOOM

            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var scaleFactor = detector.scaleFactor
            val origScale = saveScale
            saveScale *= scaleFactor
            if (saveScale > maxScale) {
                if (!isFullScreen && canGoFullScreen) {
                    minScale = maxScale
                    maxScale *= 4
                    isFullScreen = true
                    changeToNormalScreen = false
                }
                saveScale = maxScale
                scaleFactor = maxScale / origScale
            } else if (saveScale < minScale) {
                if (isFullScreen && canGoFullScreen) {
                    maxScale = minScale
                    minScale = 1f
                    isFullScreen = false
                    changeToNormalScreen = true
                }
                saveScale = minScale
                scaleFactor = minScale / origScale
            }
            viewRight = width * saveScale - width - 2f * redundantXSpace * saveScale
            viewBottom = height * saveScale - height - 2f * redundantYSpace * saveScale
            if (origWidth * saveScale <= width || origHeight * saveScale <= height) {
                newMatrix.postScale(scaleFactor, scaleFactor, width / 2, height / 2)
                if (scaleFactor < 1) {
                    newMatrix.getValues(arrayMatrix)
                    val x = arrayMatrix[Matrix.MTRANS_X]
                    val y = arrayMatrix[Matrix.MTRANS_Y]
                    if (scaleFactor < 1) {
                        if (Math.round(origWidth * saveScale) < width) {
                            if (y < -viewBottom)
                                newMatrix.postTranslate(0f, -(y + viewBottom))
                            else if (y > 0)
                                newMatrix.postTranslate(0f, -y)
                        } else {
                            if (x < -viewRight)
                                newMatrix.postTranslate(-(x + viewRight), 0f)
                            else if (x > 0)
                                newMatrix.postTranslate(-x, 0f)
                        }
                    }
                }
            } else {
                newMatrix.postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
                newMatrix.getValues(arrayMatrix)
                val x = arrayMatrix[Matrix.MTRANS_X]
                val y = arrayMatrix[Matrix.MTRANS_Y]
                if (scaleFactor < 1) {
                    if (x < -viewRight)
                        newMatrix.postTranslate(-(x + viewRight), 0f)
                    else if (x > 0)
                        newMatrix.postTranslate(-x, 0f)
                    if (y < -viewBottom)
                        newMatrix.postTranslate(0f, -(y + viewBottom))
                    else if (y > 0)
                        newMatrix.postTranslate(0f, -y)
                }
            }
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
            if (canGoFullScreen && dragFullScreen) {
                if (isFullScreen) {
                    goFullScreen()
                } else if (changeToNormalScreen) {
                    goNormalSize()
                }
            }
            super.onScaleEnd(detector)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        width = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        height = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        if (isCreating) {
            originalImageViewWidth = width
            originalImageViewHeight = height
            isCreating = false
        }
        val scale: Float
        val scaleX = width / bitmapWidth
        val scaleY = height / bitmapHeight
        scale = Math.min(scaleX, scaleY)
        if (isFitStyle) {
            newMatrix.setScale(scaleX, scaleY)
            origWidth = width - 2
            origHeight = height - 2
        } else {
            newMatrix.setScale(scale, scale)
            imageMatrix = newMatrix
            saveScale = MIN_SCALE

            redundantYSpace = height - scale * bitmapHeight
            redundantXSpace = width - scale * bitmapWidth
            redundantYSpace /= 2f
            redundantXSpace /= 2f

            newMatrix.postTranslate(redundantXSpace, redundantYSpace)

            origWidth = width - 2 * redundantXSpace
            origHeight = height - 2 * redundantYSpace
            viewRight = width * saveScale - width - 2f * redundantXSpace * saveScale
            viewBottom = height * saveScale - height - 2f * redundantYSpace * saveScale
        }
        imageMatrix = newMatrix
    }

    fun goFullScreen() {
        val margin = resources.getDimensionPixelSize(R.dimen.spacing_medium)
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        width = metrics.widthPixels.toFloat()
        height = metrics.heightPixels.toFloat()
        val layoutParams = RelativeLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels)
        layoutParams.setMargins(margin, margin, margin, margin)
        setLayoutParams(layoutParams)
    }

    fun goNormalSize() {
        val margin = resources.getDimensionPixelSize(R.dimen.spacing_medium)
        width = originalImageViewWidth
        height = originalImageViewHeight
        val layoutParams = RelativeLayout.LayoutParams(width.toInt(), height.toInt())
        layoutParams.setMargins(margin, margin, margin, margin)
        setLayoutParams(layoutParams)
    }

    private fun handleTaps() {
        isTouching = !isTouching
        if (canGoFullScreen && doubleTapFullScreen) {

            if (changeToNormalScreen) {
                changeToNormalScreen = false
                maxScale = minScale
                minScale = MIN_SCALE
                goNormalSize()
            } else {
                changeToNormalScreen = true
                isFullScreen = true
                minScale = maxScale
                maxScale *= MAX_SCALE
                goFullScreen()
            }
        }
    }

    private fun applyAttributes(context: Context, attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ZoomableViewStyle, 0, 0).apply {
            try {
                isFitStyle = getBoolean(R.styleable.ZoomableViewStyle_isFitStyle, false)
            } finally {
                recycle()
            }
        }
    }
}

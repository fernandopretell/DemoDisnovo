package com.limapps.baseui.views.loaders

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.widget.ImageView
import com.limapps.baseui.R
import kotlin.properties.Delegates

open class LoaderRoundedImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ImageView(context, attrs, defStyleAttr), LoaderView, Cloneable {

    private var loaderController: LoaderController by Delegates.notNull<LoaderController>()
    private var color: ColorStateList? = null
    private var imageViewRadius = DEFAULT_RADIUS
    private val clipPath = Path()
    private lateinit var rect: RectF

    private fun init(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoaderView, 0, 0)
        color = typedArray.getColorStateList(R.styleable.LoaderView_loader_color)
        loaderController = LoaderController(this)
        loaderController.setUseGradient(typedArray.getBoolean(R.styleable.LoaderView_use_gradient,
                LoaderController.USE_GRADIENT_DEFAULT))

        typedArray.let {
            val attributesCount = typedArray.indexCount
            (0..attributesCount - 1)
                    .mapIndexed { index, _ -> it.getIndex(index) }
                    .filter { it == R.styleable.rounded_imageView_radius }
                    .forEach { attr -> imageViewRadius = it.getDimension(attr, 0f) }
            it.recycle()
        }

        rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    fun resetLoader() {
        if (drawable != null) {
            super.setImageDrawable(null)
            loaderController.startLoading()
        }
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        loaderController.onSizeChanged()
    }

    override fun onDraw(canvas: Canvas) {
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        clipPath.addRoundRect(rect, imageViewRadius, imageViewRadius, Path.Direction.CW)
        canvas.clipPath(clipPath)
        loaderController.onDraw(canvas)
        super.onDraw(canvas)
    }

    public override fun clone(): LoaderRoundedImageView {
        val loaderRoundedImageView = LoaderRoundedImageView(context)
        loaderRoundedImageView.layoutParams = layoutParams
        loaderRoundedImageView.setImageDrawable(drawable)
        loaderRoundedImageView.scaleType = scaleType
        loaderRoundedImageView.id = (Math.random() * 100).toInt()
        return loaderRoundedImageView
    }

    override fun setRectColor(rectPaint: Paint) {
        rectPaint.color = if (color != null) color!!.defaultColor else ContextCompat.getColor(context, R.color.gray)
    }

    override fun valueSet(): Boolean = drawable != null

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        loaderController.stopLoading()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        loaderController.stopLoading()
    }

    override fun setImageIcon(icon: Icon?) {
        super.setImageIcon(icon)
        loaderController.stopLoading()
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        loaderController.stopLoading()
    }

    companion object {
        private const val DEFAULT_RADIUS = 0.0f
    }

    init {
        init(attrs)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        loaderController.stopLoading()
    }
}
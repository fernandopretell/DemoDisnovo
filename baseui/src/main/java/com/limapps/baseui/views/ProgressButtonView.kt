package com.limapps.baseui.views

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.limapps.baseui.R
import com.limapps.common.show

@BindingMethods(value = [
    BindingMethod(
            type = ProgressButtonView::class,
            attribute = "app:show_progress",
            method = "showProgressBar")])

class ProgressButtonView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val imageView by lazy { findViewById<ImageView>(R.id.imageView) }
    val buttonTitle by lazy { findViewById<TextView>(R.id.textView) }
    val progressBar by lazy { findViewById<ProgressBar>(R.id.progressBar) }

    private var buttonImage: Int = 0
    private var titleColor: Int = 0
    private var title: String? = null
    private var titleSize: Int = 0
    private var titleFont: Int = 0
    private var progressBarStyle: String? = null
    private var showProgressBar: Boolean? = false

    init {
        View.inflate(context, R.layout.view_progress_button, this)
        val styles = context.obtainStyledAttributes(attrs, R.styleable.ProgressButtonView)
        generateValues(styles, attrs)
        setValuesToViews()
    }

    private fun generateValues(styles: TypedArray, attrs: AttributeSet?) {
        title = styles.getString(R.styleable.ProgressButtonView_title)
        buttonImage = styles.getResourceId(R.styleable.ProgressButtonView_button_image, R.color.transparent)
        titleColor = styles.getResourceId(R.styleable.ProgressButtonView_android_textColor, R.color.black)
        titleSize = styles.getDimensionPixelSize(R.styleable.ProgressButtonView_title_size, 5)
        titleFont = styles.getResourceId(R.styleable.ProgressButtonView_title_font, 0)
        progressBarStyle = styles.getString(R.styleable.ProgressButtonView_progress_style)
        showProgressBar = styles.getBoolean(R.styleable.ProgressButtonView_show_progress, false)
    }

    private fun setValuesToViews() {
        imageView.setImageResource(buttonImage)
        buttonTitle.text = title
        buttonTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
    }

    fun showProgressBar(show: Boolean) {
        progressBar.show(show)
        buttonTitle.show(!show)
    }

    fun isEnableToExecute(): Boolean {
        return progressBar.isShown.not() && this.isEnabled
    }

}
package com.limapps.base.views

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import com.limapps.base.R
import com.limapps.base.adapters.GenericAdapterRecyclerView

class ExpandableTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : TextView(context, attrs), GenericAdapterRecyclerView.ItemView<CharSequence> {

    companion object {
        private const val DEFAULT_TRIM_LENGTH = 70
    }

    private var originalText: CharSequence? = null
    private var trimmedText: CharSequence? = null
    private var bufferType: BufferType? = null
    private var trim = true
    private var trimLength: Int = 0
    private val endText by lazy { resources.getString(R.string.toppings_read_more) }
    private val marginTop by lazy { resources.getDimensionPixelSize(R.dimen.spacing_xlarge) }
    private val marginLeft by lazy { resources.getDimensionPixelSize(R.dimen.spacing_empty) }
    private val marginRight by lazy { resources.getDimensionPixelSize(R.dimen.spacing_empty) }
    private val color by lazy { ContextCompat.getColor(context, R.color.text_color_secondary) }

    init {

        val marginLayoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams = marginLayoutParams
        gravity = GravityCompat.START
        typeface = ResourcesCompat.getFont(context, R.font.default_font_regular)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH)
        typedArray.recycle()
        setOnClickListener {
            trim = !trim
            setText()
            requestFocusFromTouch()
        }
    }

    private fun setText() = super.setText(displayableText, bufferType)

    private val displayableText: CharSequence
        get() = if (trim) trimmedText ?: "" else originalText ?: ""

    override fun setText(text: CharSequence, type: BufferType) {
        originalText = text
        trimmedText = getTrimmedText()
        bufferType = type
        setText()
    }

    private fun getTrimmedText(): CharSequence {
        if (originalText?.length ?: 0 > trimLength) {
            val wordToSpan = SpannableStringBuilder(originalText, 0, trimLength + 1).append(endText)
            val highLightTextStartValue = wordToSpan.length - endText.length + 2
            wordToSpan.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_primary)),
                    highLightTextStartValue, wordToSpan.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            return wordToSpan
        } else {
            return originalText ?: ""
        }
    }

    fun setTrimLength(trimLength: Int) {
        this.trimLength = trimLength
        trimmedText = getTrimmedText()
        setText()
    }

    override fun bind(item: CharSequence?, position: Int) {
        this.originalText = item

        val marginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.topMargin = marginTop
        marginLayoutParams.leftMargin = marginLeft
        marginLayoutParams.rightMargin = marginRight
        setTextColor(color)
        item?.let {
            text = it
        } ?: kotlin.run {
            text = ""
        }
    }

    override fun setItemClickListener(onItemClickListener: GenericAdapterRecyclerView.OnItemClickListener?) = Unit

    override fun getIdForClick(): Int = 0

    override fun getData(): CharSequence? = originalText
}
package com.limapps.baseui.views

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.Toolbar
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.limapps.baseui.R
import com.limapps.baseui.utils.ToolbarUtils
import com.limapps.common.isLollipop

class RappiToolbar : RelativeLayout {

    val titleView: TextView by lazy { findViewById<TextView>(R.id.textView_title) }
    val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar_view) }

    private var title: String? = null
    private var showBack = true
    private var showElevation = true
    private var textColor = Color.BLACK
    private var colorBackground = Color.WHITE
    private var backColor = Color.BLACK
    private var titleBold = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs)
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.layout_toolbar, this)
        initViews()
        initParams()
    }

    private fun initParams() {
        setBackgroundColor(colorBackground)

        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, android.R.attr.actionBarSize)
        if (isLollipop() && showElevation) {
            elevation = resources.getDimensionPixelSize(R.dimen.toolbar_elevation).toFloat()
        }
    }

    private fun initViews() {
        toolbar.setBackgroundColor(colorBackground)
        titleView.setTextColor(textColor)

        if (titleBold) {
            titleView.setTypeface(titleView.typeface, Typeface.BOLD)
        }

        if (showBack) {
            showNavigationIcon()
        }

        setTitle(title)
    }

    private fun showNavigationIcon() {
        toolbar.navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_white_24dp)?.apply {
            mutate()
            setColorFilter(backColor, PorterDuff.Mode.MULTIPLY)
        }
    }

    fun showNavigationIcon(@DrawableRes drawableResource: Int) {
        toolbar.navigationIcon = ContextCompat.getDrawable(context, drawableResource)?.apply {
            mutate()
            setColorFilter(textColor, PorterDuff.Mode.MULTIPLY)
        }
    }

    fun showNavigationIcon(@DrawableRes drawableResource: Int, @ColorInt colorResource: Int) {
        toolbar.navigationIcon = ContextCompat.getDrawable(context, drawableResource)?.apply {
            mutate()
            setColorFilter(colorResource, PorterDuff.Mode.MULTIPLY)
        }
    }

    @JvmOverloads
    fun setTextRightIcon(@DrawableRes drawableResource: Int, tintDrawable: Boolean = false) {
        val drawable = ContextCompat.getDrawable(context, drawableResource)
        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_medium)
        if (tintDrawable) {
            drawable?.mutate()
            drawable?.setColorFilter(textColor, PorterDuff.Mode.MULTIPLY)
        }
        titleView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        titleView.compoundDrawablePadding = spacing
    }

    fun inflateMenu(@MenuRes menuId: Int) = toolbar.inflateMenu(menuId)

    fun getMenuItemAtPosition(position: Int): MenuItem? = toolbar.menu?.getItem(position)

    fun setOnMenuItemClickListener(onMenuItemClickListener: Toolbar.OnMenuItemClickListener) {
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener)
    }

    private fun initAttrs(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.rappi_toolbar)

        if (typedArray != null) {
            val size = typedArray.indexCount
            (0 until size).asSequence()
                    .map { typedArray.getIndex(it) }
                    .forEach {
                        when (it) {
                            R.styleable.rappi_toolbar_show_back -> showBack = typedArray.getBoolean(it, true)
                            R.styleable.rappi_toolbar_back_color -> backColor = typedArray.getColor(it, Color.BLACK)
                            R.styleable.rappi_toolbar_show_elevation -> showElevation = typedArray.getBoolean(it, true)
                            R.styleable.rappi_toolbar_toolbar_text -> title = typedArray.getString(it)
                            R.styleable.rappi_toolbar_toolbar_text_color -> textColor = typedArray.getColor(it, Color.BLACK)
                            R.styleable.rappi_toolbar_background_color -> colorBackground = typedArray.getColor(it, Color.WHITE)
                            R.styleable.rappi_toolbar_back_color -> colorBackground = typedArray.getColor(it, Color.WHITE)
                            R.styleable.rappi_toolbar_title_bold -> titleBold = typedArray.getBoolean(it, false)
                        }
                    }
            typedArray.recycle()
        }
    }

    fun setTitle(titleResource: Int) = titleView.setText(titleResource)

    fun setTitle(title: String?) {
        titleView.text = title
    }

    fun setBackButtonVisible(visible: Boolean) {
        if (visible) {
            showNavigationIcon()
        } else {
            toolbar.navigationIcon = null
        }
    }

    override fun setOnClickListener(listener: View.OnClickListener?) {
        toolbar.setNavigationOnClickListener { view ->
            view.id = R.id.button_back
            listener?.onClick(view)
        }
    }

    fun setBackButtonColor(color: Int) {
        ToolbarUtils.tintMenuItemIconWithResource(color, toolbar.menu.getItem(0))
    }
}

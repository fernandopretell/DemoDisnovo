package com.limapps.base.binding

import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.limapps.base.R
import com.limapps.base.others.ResourcesProvider
import com.limapps.baseui.animations.SHORT_DURATION
import com.limapps.baseui.animations.animateFadeIn
import com.limapps.baseui.animations.animateFadeOut
import com.limapps.baseui.utils.ToolbarUtils
import com.limapps.baseui.views.RappiToolbar
import com.limapps.baseui.views.Slice

@BindingAdapter("show_back")
fun RappiToolbar.showBackToolbar(show: Boolean) {
    this.setBackButtonVisible(show)
}

@BindingAdapter("toolbar_text")
fun RappiToolbar.setToolbarTitle(title: String) {
    this.setTitle(title)
}

@BindingAdapter("toolbar_background_color")
fun RappiToolbar.setToolbarBackgroundColor(@ColorRes color: Int) {
    this.setBackgroundColor(ContextCompat.getColor(toolbar.context, color))
}

@BindingAdapter("toolbar_text_color")
fun RappiToolbar.setToolbarTextColor(@ColorRes color: Int) {
    this.titleView.setTextColor(ContextCompat.getColor(toolbar.context, color))
}

@BindingAdapter("toolbar_text")
fun Toolbar.setToolbarTitle(title: String) {
    this.title = title
}

@BindingAdapter(value = ["back_icon", "icon_color"], requireAll = false)
fun RappiToolbar.setNavigationIconOnToolbar(@DrawableRes icon: Int, @ColorRes iconColor: Int) {
    val context = this.context
    if (icon != 0 && context != null) {
        if (iconColor != 0) {
            this.showNavigationIcon(icon, ContextCompat.getColor(context, iconColor))
        } else {
            this.showNavigationIcon(icon)
        }
    }
}

@BindingAdapter("text_drawable")
fun RappiToolbar.setTextIconOnToolbar(@DrawableRes icon: Int) {
    this.setTextRightIcon(icon)
}

@BindingAdapter("divider_decoration")
fun androidx.recyclerview.widget.RecyclerView.setRecyclerViewSpace(dividerItemDecoration: androidx.recyclerview.widget.RecyclerView.ItemDecoration) {
    this.addItemDecoration(dividerItemDecoration)
}

@BindingAdapter("alpha")
fun View.setAlphaOnView(alpha: Float) {
    this.alpha = alpha
    this.visibility = if (alpha == 0f) View.GONE else View.VISIBLE
}


@BindingAdapter(value = ["menu_drawable", "menu_listener", "menu_color"], requireAll = false)
fun RappiToolbar.setMenuOnToolbar(@MenuRes menuId: Int,
                                  listener: Toolbar.OnMenuItemClickListener,
                                  @ColorRes menuColor: Int) {
    this.inflateMenu(menuId)
    if (menuColor != 0) {
        ToolbarUtils.tintMenuItemIconWithResource(ResourcesProvider.getColor(menuColor), this.getMenuItemAtPosition(0))
    }
    this.setOnMenuItemClickListener(listener)
}

@BindingAdapter(value = ["tint_color_button"])
fun ImageButton.setTintColorImageButton(@ColorRes imageColor: Int) {
    val color = ContextCompat.getColor(this.context, imageColor)
    val drawable = this.drawable
    if (drawable != null) {
        DrawableCompat.setTint(drawable, color)
    }
}

@BindingAdapter(value = ["selectedPosition", "selectedPositionAttrChanged"], requireAll = false)
fun Spinner.bindSpinnerData(newSelectedPosition: Int, selectedAttrChanged: InverseBindingListener) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            selectedAttrChanged.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {}
    }
    if (newSelectedPosition != -1) {
        this.setSelection(newSelectedPosition, true)
    }
}

@InverseBindingAdapter(attribute = "selectedPosition", event = "selectedPositionAttrChanged")
fun Spinner.captureSelectedValue(): Int {
    return this.selectedItemPosition
}

@BindingAdapter("ime_action_listener")
fun EditText.onImeAction(listener: TextView.OnEditorActionListener) {
    this.setOnEditorActionListener(listener)
}

@BindingAdapter("slice_color")
fun Slice.setSliceColor(@ColorRes colorRes: Int) {
    this.setColor(ResourcesProvider.getColor(colorRes))
}

@BindingAdapter("color_background")
fun View.setBackgroundResourceColor(@DrawableRes color: Int) {
    this.background = ResourcesProvider.getDrawable(color)
}

@BindingAdapter("color_background_shape")
fun View.setBackgroundResourceColorShape(@ColorRes color: Int) {
    val drawable = this.background.mutate()
    val gradientDrawable: GradientDrawable
    gradientDrawable = if (drawable is LayerDrawable) {
        drawable.findDrawableByLayerId(R.id.shape) as GradientDrawable
    } else {
        drawable as GradientDrawable
    }
    gradientDrawable.setColor(ResourcesProvider.getColor(color))
}

@BindingAdapter("listItems")
fun LinearLayout.addItemsToLinearLayout(items: List<View>) {
    for (item in items) {
        this.addView(item)
    }
}

@BindingAdapter("isFontBold")
fun TextView.changeFontStyle(bold: Boolean) {
    this.typeface = ResourcesProvider.getFontTypeFace(if (bold) R.font.default_font_bold else R.font.default_font_regular)
}

@BindingAdapter("isUnderline")
fun TextView.toggleUnderLine(isUnderline: Boolean) {
    val flags = this.paintFlags
    this.paintFlags = if (isUnderline) flags or Paint.UNDERLINE_TEXT_FLAG else flags and Paint.UNDERLINE_TEXT_FLAG.inv()
}

@BindingAdapter("fade_out")
fun View.dismmissFadeOut(disappear: Boolean) {
    if (disappear) {
        this.animateFadeOut(SHORT_DURATION, 0, null)
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter("fade_in")
fun View.appearFadeIn(appear: Boolean) {
    if (appear) {
        this.animateFadeIn(1f, SHORT_DURATION, 0, null)
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("focus_listener")
fun EditText.focusChangeListener(focusChangeListener: View.OnFocusChangeListener) {
    this.onFocusChangeListener = focusChangeListener
}

@BindingAdapter("layout_height")
fun View.setLayoutHeight(height: Float) {
    val layoutParams = this.layoutParams
    layoutParams.height = height.toInt()
    this.layoutParams = layoutParams
}

@BindingAdapter(value = ["resource_left", "resource_right", "resource_top", "resource_bottom"], requireAll = false)
fun TextView.setTextViewDrawable(@DrawableRes drawableLeft: Int,
                                 @DrawableRes drawableRight: Int,
                                 @DrawableRes drawableTop: Int,
                                 @DrawableRes drawableBottom: Int) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableLeft, drawableTop,
            drawableRight, drawableBottom)
}

@BindingAdapter(value = ["background_color", "background_corner_radius"])
fun View.setBackgroundRounded(@ColorInt backgroundColor: Int, cornerRadius: Float) {
    GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setColor(backgroundColor)
        setCornerRadius(cornerRadius)
    }.let { background = it }
}

@BindingAdapter(value = ["background_color_circular"])
fun View.setBackgroundCircular(@ColorInt backgroundColor: Int) {
    GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setColor(backgroundColor)
    }.let { background = it }
}

@BindingAdapter(value = ["background_colors_gradient", "background_corner_radius"])
fun View.setBackgroundColorsRounded(@ColorInt backgroundColors: IntArray, cornerRadius: Float) {
    GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,backgroundColors).apply {
        shape = GradientDrawable.RECTANGLE
        setCornerRadius(cornerRadius)
    }.let { background = it }
}

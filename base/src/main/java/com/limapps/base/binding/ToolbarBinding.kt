package com.limapps.base.binding

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import android.graphics.PorterDuff
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.Toolbar
import com.limapps.base.ViewModel
import com.limapps.base.others.ResourcesProvider
import com.limapps.baseui.utils.ToolbarUtils

object ToolbarBinding {

    @JvmStatic
    @BindingAdapter(value = ["collapsing_title", "collapsing_title_color"], requireAll = false)
    fun setToolbarTitle(collapsingToolbar: CollapsingToolbarLayout, title: String, @ColorRes colorResource: Int) {
        collapsingToolbar.title = title
        if (colorResource != 0) {
            val color = ResourcesProvider.getColor(colorResource)
            collapsingToolbar.apply {
                setCollapsedTitleTextColor(color)
                setExpandedTitleColor(color)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["toolbar_text", "toolbar_text_color"], requireAll = false)
    fun setToolbarTitle(toolbar: Toolbar, title: String, @ColorRes color: Int) {
        toolbar.title = title
        if (color != 0) {
            toolbar.setTitleTextColor(ResourcesProvider.getColor(color))
        }
    }

    @JvmStatic
    @BindingAdapter("toolbar_text")
    fun setToolbarTitle(toolbar: Toolbar, title: ObservableField<String>) {
        toolbar.title = title.get()
    }

    @JvmStatic
    @BindingAdapter(value = ["menu_drawable", "menu_listener", "tint_color"], requireAll = false)
    fun setMenuOnToolbar(toolbar: Toolbar, @MenuRes menuId: Int,
                         listener: Toolbar.OnMenuItemClickListener? = null, @ColorRes color: Int = 0) {
        ToolbarUtils.clearMenu(toolbar)
        if (menuId != 0) {
            toolbar.inflateMenu(menuId)
            if (color != 0) {
                val size = toolbar.menu.size()
                for (i in 0 until size) {
                    ToolbarUtils.tintMenuItemIconWithResource(ResourcesProvider.getColor(color), toolbar.menu.getItem(i))
                }
            }
            listener?.let { toolbar.setOnMenuItemClickListener(listener) }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["ic_drawable", "ic_color", "callback_viewModel"], requireAll = false)
    fun setInitialToolbarConfig(toolbar: Toolbar, @DrawableRes drawable: Int, @ColorRes color: Int = 0,
                                viewModel: ViewModel? = null) {
        val context = toolbar.context
        if (drawable != 0 && context != null) {
            val icon = ContextCompat.getDrawable(context, drawable)
            if (color != 0 && icon != null) {
                icon.mutate()
                icon.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.MULTIPLY)
            }
            toolbar.navigationIcon = icon
        }
        viewModel?.let { toolbar.setNavigationOnClickListener { viewModel.onClickIconHeader() } }
    }
}
package com.limapps.baseui.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.databinding.BindingAdapter

object DialogBindings {

    @JvmStatic
    @BindingAdapter("html_text")
    fun TextView.formatHtml(action: String?) {
        action?.let {
            text = fromHtml(it)
        }
    }

    private fun fromHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }
}
package com.limapps.baseui.views.tooltip

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import android.text.SpannableStringBuilder
import android.view.Gravity

data class ToolTip(val context: Context) {

    lateinit var builder: Builder

    companion object {

        class Builder(val context: Context) {

            var message = SpannableStringBuilder()
            var backgroundColor: Int = 0
            var backgroundResource: Int = 0
            var padding: Int = 0
            var gravity: Int = Gravity.TOP
            var verticalAdjustment: Int = 0
            var dismissTime: Int = -1
            var onClickDismiss: Boolean = false

            fun backgroundResource(@DrawableRes resId: Int) = apply { this.backgroundResource = resId }

            fun backgroundColor(@ColorRes resId: Int) = apply { this.backgroundColor = ContextCompat.getColor(context, resId) }

            fun padding(@DimenRes resId: Int) = apply { this.padding = context.resources.getDimensionPixelSize(resId) }

            fun gravity(gravity: Int) = apply { this.gravity = gravity }

            fun message(message: SpannableStringBuilder) = apply { this.message = message }

            fun verticalAdjustment(verticalAdjustment: Int) = apply { this.verticalAdjustment = verticalAdjustment }

            fun dismissTime(dismissTime: Int) = apply { this.dismissTime = dismissTime }

            fun onClickDismiss(onClickDismiss: Boolean) = apply { this.onClickDismiss = onClickDismiss }
        }
    }
}
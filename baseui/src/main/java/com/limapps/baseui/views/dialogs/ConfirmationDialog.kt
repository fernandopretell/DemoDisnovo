package com.limapps.baseui.views.dialogs

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.limapps.baseui.R
import com.limapps.baseui.databinding.DialogConfirmationBinding
import com.limapps.common.setElevation


@SuppressLint("ViewConstructor")
class ConfirmationDialog private constructor(context: Context) : FrameLayout(context) {

    private val confirmationDialogModel = ConfirmationDialogModel()

    var listener: ConfirmationListener? = null

    private val binding: DialogConfirmationBinding by lazy {
        DataBindingUtil.inflate<DialogConfirmationBinding>(LayoutInflater.from(context),
                R.layout.dialog_confirmation, this, false)
    }

    init {
        binding.view = confirmationDialogModel
        layoutParams = ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
        isClickable = true
        setBackgroundResource(R.color.black_transparent_4)
        setElevation(resources.getDimensionPixelSize(R.dimen.spacing_xlarge))
        val margin = resources.getDimensionPixelSize(R.dimen.spacing_xxlarge)
        val layoutParams = LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(margin, 0, margin, 0)
        layoutParams.gravity = Gravity.CENTER
        addView(binding.root, layoutParams)
        setOnClickListener { dismiss() }
    }

    private constructor(context: Context, builder: Builder) : this(context) {
        confirmationDialogModel.title.set(builder.title.capitalize())
        builder.deepLinkText?.isNotEmpty()?.let {
            confirmationDialogModel.deepLinkText.set(builder.deepLinkText)
            confirmationDialogModel.hasDeepLink.set(true)
            binding.textViewHiperlink.setTextColor(builder.textColorDeepLink)
        }
        if (builder.description.isNotBlank()) {
            confirmationDialogModel.description.set(builder.description)
            confirmationDialogModel.hasDescription.set(true)
        }
        confirmationDialogModel.message.set(builder.message)
        confirmationDialogModel.primaryButtonText.set(builder.primaryButton)
        if (builder.secondaryButton.isNotBlank()) {
            confirmationDialogModel.hasSecondaryButton.set(true)
            confirmationDialogModel.secondaryButtonText.set(builder.secondaryButton)
        }
        builder.iconImage?.let {
            confirmationDialogModel.hasIcon.set(true)
            binding.imageViewIcon.setImageDrawable(ContextCompat.getDrawable(context, it))
        }
        binding.textViewHiperlink.setOnClickListener {
            (parent as ViewGroup).removeView(this)
            listener?.clickDeepLink()
        }
        binding.buttonRemove.setOnClickListener {
            primaryButtonClick()
        }
        binding.buttonSecondary.setOnClickListener {
            secondaryButtonClick()
        }
    }

    private fun primaryButtonClick() {
        removeView()
        listener?.primaryButtonHandle()
    }

    private fun secondaryButtonClick() {
        removeView()
        listener?.secondaryButtonHandle()
    }

    private fun removeView() {
        (parent as? ViewGroup)?.removeView(this)
    }

    private fun dismiss() {
        removeView()
        listener?.onDismiss()
    }

    fun confirmDeletion() {
        dismiss()
    }

    companion object {
        class Builder constructor(private val context: Context) {

            lateinit var title: String

            var description: String = ""

            var message: String? = null

            var primaryButton: String = context.getString(R.string.baseui_ok)

            var secondaryButton: String = ""

            var textColorDeepLink = R.color.grey_charcoil

            @DrawableRes
            var iconImage: Int? = null

            var deepLinkText: String? = null

            fun title(title: String) = apply { this.title = title }

            fun title(@StringRes resId: Int) = apply { this.title = context.getString(resId) }

            fun icon(@DrawableRes resId: Int) = apply { this.iconImage = resId }

            fun deepLinkText(deepLinkText: String) = apply { this.deepLinkText = deepLinkText }

            fun setColorDeepLink(@ColorRes color: Int) = apply { this.textColorDeepLink = color }

            fun description(description: String) = apply { this.description = description }

            fun description(@StringRes resId: Int) = apply { this.description = context.getString(resId) }

            fun message(message: String) = apply { this.message = message }

            fun message(@StringRes resId: Int) = apply { this.message = context.getString(resId) }

            fun primaryButton(primaryButton: String) = apply { this.primaryButton = primaryButton }

            fun primaryButton(@StringRes resId: Int) = apply { this.primaryButton = context.getString(resId) }

            fun secondaryButton(secondaryButton: String) = apply { this.secondaryButton = secondaryButton }

            fun secondaryButton(@StringRes resId: Int) = apply { this.secondaryButton = context.getString(resId) }

            fun build() = ConfirmationDialog(context, this)
        }
    }

    interface ConfirmationListener {
        fun onDismiss()

        fun secondaryButtonHandle()

        fun primaryButtonHandle()

        fun clickDeepLink()
    }
}
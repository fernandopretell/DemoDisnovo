package com.limapps.baseui.views.dialogs

import android.annotation.SuppressLint
import android.content.Context
import android.text.method.LinkMovementMethod
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.limapps.baseui.R
import com.limapps.baseui.databinding.DialogConfirmationTitleImageBinding
import com.limapps.common.setElevation

@SuppressLint("ViewConstructor")
class ConfirmationTitleImageDialog private constructor(context: Context) : FrameLayout(context) {

    val title = ObservableField<String>()
    val description = ObservableField<String>()
    val primaryButtonText = ObservableField<String>()
    val message = ObservableField<String>()
    val hasDescription = ObservableBoolean()

    var listener: ConfirmationTitleImageListener? = null

    val binding: DialogConfirmationTitleImageBinding by lazy {
        DataBindingUtil.inflate<DialogConfirmationTitleImageBinding>(LayoutInflater.from(context),
                R.layout.dialog_confirmation_title_image, this, false)
    }

    init {

        binding.view = this

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

        setOnClickListener { dismissDialog() }
        binding.textViewDescription.movementMethod = LinkMovementMethod.getInstance()
    }

    private constructor(context: Context, builder: Builder) : this(context) {
        title.set(builder.title)
        if (builder.description.isNotBlank()) {
            description.set(builder.description)
            hasDescription.set(true)
        }

        builder.message?.let { message.set(it) }
                ?: kotlin.run { binding.textViewMessage.visibility = View.INVISIBLE }
        message.set(builder.message)
        builder.iconImage?.let {
            binding.imageViewIcon.setImageDrawable(ContextCompat.getDrawable(context, it))
        }
        primaryButtonText.set(builder.primaryButton)
    }

    private fun dismissDialog() {
        (parent as ViewGroup).removeView(this)
        listener?.dismissDialog()
    }


    fun confirmDeletion() {
        dismissDialog()
    }

    companion object {
        class Builder constructor(private val context: Context) {
            lateinit var title: String
            var description: String = ""
            var message: String? = null
            var primaryButton: String = context.getString(R.string.baseui_ok)
            @DrawableRes
            var iconImage: Int? = null

            fun title(title: String) = apply { this.title = title }

            fun title(@StringRes resId: Int) = apply { this.title = context.getString(resId) }

            fun icon(@DrawableRes resId: Int) = apply { this.iconImage = resId }

            fun description(description: String) = apply { this.description = description }

            fun description(@StringRes resId: Int) = apply { this.description = context.getString(resId) }

            fun message(message: String) = apply { this.message = message }

            fun message(@StringRes resId: Int) = apply { this.message = context.getString(resId) }

            fun primaryButton(primaryButton: String) = apply { this.primaryButton = primaryButton }

            fun primaryButton(@StringRes resId: Int) = apply { this.primaryButton = context.getString(resId) }

            fun build() = ConfirmationTitleImageDialog(context, this)
        }
    }

    interface ConfirmationTitleImageListener {
        fun dismissDialog()
    }
}

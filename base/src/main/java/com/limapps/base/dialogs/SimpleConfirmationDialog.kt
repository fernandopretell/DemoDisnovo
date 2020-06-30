package com.limapps.base.dialogs

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.annotation.StringRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.limapps.base.R
import com.limapps.base.databinding.DialogSimpleConfirmationBinding
import com.limapps.base.others.Callback
import com.limapps.common.removeFromParent
import com.limapps.common.setElevation

@SuppressLint("ViewConstructor")
class SimpleConfirmationDialog<T : Any> private constructor(context: Context) : FrameLayout(context) {

    val title = ObservableField<String>()
    val description = ObservableField<String>()
    val primaryButtonText = ObservableField<String>()
    val secondaryButtonText = ObservableField<String>()
    val hasDescription = ObservableBoolean()

    private lateinit var callback: Callback<T>
    private var data: T? = null
    private var unifyResolveAndFinish: Boolean = true


    init {
        val binding = DataBindingUtil.inflate<DialogSimpleConfirmationBinding>(LayoutInflater.from(context),
                R.layout.dialog_simple_confirmation, this, false)
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
    }

    private constructor(context: Context, builder: Builder<T>) : this(context) {
        title.set(builder.title)
        if (builder.description.isNotBlank()) {
            description.set(builder.description)
            hasDescription.set(true)
        }
        primaryButtonText.set(builder.primaryButton)
        if (builder.secondaryButton.isNullOrBlank()) {
            secondaryButtonText.set(context.getString(R.string.cancel))
        } else {
            secondaryButtonText.set(builder.secondaryButton)
        }
        callback = builder.callback
        data = builder.data
        unifyResolveAndFinish = builder.unifyResolveAndFinish
    }

    fun dismissDialog() {
        callback.onFinish()
        removeFromParent()
    }

    fun confirmDeletion() {
        callback.onResolve(data!!)
        if (unifyResolveAndFinish) dismissDialog() else removeFromParent()
    }

    companion object {
        class Builder<T : Any> constructor(private val context: Context) {
            lateinit var title: String
            var description: String = ""
            lateinit var primaryButton: String
            var secondaryButton: String? = null
            lateinit var callback: Callback<T>
            var data: T? = null
            var unifyResolveAndFinish: Boolean = true

            fun title(title: String) = apply { this.title = title }

            fun title(@StringRes resId: Int) = apply { this.title = context.getString(resId) }

            fun description(description: String) = apply { this.description = description }

            fun description(@StringRes resId: Int) = apply { this.description = context.getString(resId) }

            fun primaryButton(primaryButton: String) = apply { this.primaryButton = primaryButton }

            fun primaryButton(@StringRes resId: Int) = apply { this.primaryButton = context.getString(resId) }

            fun secondaryButton(secondaryString: String) = apply { this.secondaryButton = secondaryString }

            fun secondaryButton(@StringRes resId: Int) = apply { this.secondaryButton = context.getString(resId) }

            fun unifyResolveAndFinish(value: Boolean) = apply { this.unifyResolveAndFinish = value }

            fun callback(callback: Callback<T>) = apply { this.callback = callback }

            fun data(data: T) = apply { this.data = data }

            fun build() = SimpleConfirmationDialog(context, this)
        }
    }
}

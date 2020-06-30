package com.limapps.base.views

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.limapps.base.R
import com.limapps.base.databinding.ViewButtonTipBinding

class ButtonTip @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val tipValue: ObservableField<String> = ObservableField()
    val isTipSelected: ObservableBoolean = ObservableBoolean()


    private val binding: ViewButtonTipBinding by lazy {
        ViewButtonTipBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.viewModel = this
    }

    fun setSelectedType(isSelected: Boolean) {
        isTipSelected.set(isSelected)
        setTextColor(if (isSelected) {
            R.color.color_primary
        } else {
            R.color.text_color_secondary
        })
    }

    fun setTextColor(color: Int) {
        binding.textViewTip.setTextColor(ContextCompat.getColor(context, color))
    }


}
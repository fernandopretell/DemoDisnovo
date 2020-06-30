package com.limapps.base.views

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout

import com.limapps.base.R
import com.limapps.base.adapters.GenericAdapterRecyclerView
import com.limapps.baseui.others.OnClickViewListener

class ButtonView(context: Context) : FrameLayout(context), GenericAdapterRecyclerView.ItemView<String> {

    companion object {
        const val BUTTON_VIEW = "ButtonView"
    }

    private val button: Button by lazy {
        inflate(context, R.layout.view_button, null) as Button
    }

    init {
        layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val layoutParamsButton = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParamsButton.gravity = Gravity.CENTER_HORIZONTAL
        layoutParamsButton.topMargin = resources.getDimensionPixelSize(R.dimen.spacing_large)
        layoutParamsButton.bottomMargin = layoutParamsButton.topMargin
        addView(button, layoutParamsButton)
    }

    override fun bind(item: String, position: Int) {
        button.text = item
    }

    override fun setItemClickListener(onItemClickListener: GenericAdapterRecyclerView.OnItemClickListener) {
        button.setOnClickListener(object : OnClickViewListener() {
            override fun onClickView(view: View) {
                onItemClickListener.onItemClicked(this@ButtonView)
            }
        })
    }

    override fun getIdForClick(): Int = 0

    override fun getData(): String = BUTTON_VIEW
}
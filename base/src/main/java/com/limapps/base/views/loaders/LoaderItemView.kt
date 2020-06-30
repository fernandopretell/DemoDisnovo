package com.limapps.base.views.loaders

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.limapps.base.R
import com.limapps.base.adapters.GenericAdapterRecyclerView
import com.limapps.baseui.views.loaders.LoaderFrameLayout

class LoaderItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LoaderFrameLayout(context, attrs, defStyleAttr), GenericAdapterRecyclerView.ItemView<Int> {

    init {
        attrs?.let(::inflateCustomAttributes)
        layoutParams = MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun bind(item: Int, position: Int) {
        removeAllViews()
        addView(View.inflate(context, item, null))
    }

    override fun setItemClickListener(onItemClickListener: GenericAdapterRecyclerView.OnItemClickListener?) = Unit

    override fun getIdForClick() = 0

    override fun getData() = 0

    private fun inflateCustomAttributes(attrs: AttributeSet) = context.theme
            .obtainStyledAttributes(attrs, R.styleable.LoaderItemView, 0, 0)
            .run {
                getResourceId(R.styleable.LoaderItemView_layout, -1).takeIf { it != -1 }?.let {
                    addView(View.inflate(context, it, null))
                }
            }
}
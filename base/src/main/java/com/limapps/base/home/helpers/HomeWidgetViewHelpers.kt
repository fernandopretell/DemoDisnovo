package com.limapps.base.home.helpers

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.limapps.base.adapters.GenericAdapterRecyclerView

fun View.setupDimensions(width: Int, height: Int, margins: Int) {
    if (layoutParams == null) {
        layoutParams = MarginLayoutParams(0, 0)
    }
    layoutParams.apply {
        this.width = width
        this.height = height

        if (this is MarginLayoutParams) {
            this.topMargin = margins
            this.bottomMargin = margins
        }
    }
    requestLayout()
}

fun View.hide() {
    setupDimensions(0, 0, 0)
}

package com.limapps.baseui.others

import android.os.Handler
import android.view.View

abstract class OnClickViewListener @JvmOverloads constructor(private var duration: Int = SHORT_DURATION)
    : View.OnClickListener {

    companion object {

        private var isClickable = true

        const val SHORT_DURATION = 500
        const val MEDIUM_DURATION = 750
        const val LONG_DURATION = 1000
    }

    override fun onClick(view: View) {
        if (isClickable) {
            isClickable = false
            onClickView(view)
            Handler().postDelayed({ isClickable = true }, duration.toLong())
        }
    }

    abstract fun onClickView(view: View)
}
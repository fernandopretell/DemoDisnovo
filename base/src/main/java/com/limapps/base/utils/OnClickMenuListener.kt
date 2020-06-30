package com.limapps.base.utils

import android.os.Handler
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem

interface OnClickMenuListener : Toolbar.OnMenuItemClickListener {

    var duration: Int

    companion object {

        private var isClickable = true

        val SHORT_DURATION = 500
        val MEDIUM_DURATION = 750
        val LONG_DURATION = 1000
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (isClickable) {
            isClickable = false
            onClickItem(item.itemId)
            Handler().postDelayed({ isClickable = true }, duration.toLong())
        }
        return isClickable
    }

    fun onClickItem(id: Int) {}
}

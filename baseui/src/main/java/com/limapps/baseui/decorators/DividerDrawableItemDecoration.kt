package com.limapps.baseui.decorators

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.limapps.common.forEach

class DividerDrawableItemDecoration(private val drawable: Drawable?) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = drawable.getSaveIntrinsicHeight()
        }
    }

    override fun onDraw(canvas: Canvas, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        val dividerStart = parent.paddingStart
        val dividerEnd = parent.width - parent.paddingRight
        parent.forEach {
            val params = it.layoutParams as androidx.recyclerview.widget.RecyclerView.LayoutParams

            val dividerTop = it.bottom + params.bottomMargin
            val dividerBottom = dividerTop + drawable.getSaveIntrinsicHeight()

            drawable?.setBounds(dividerStart, dividerTop, dividerEnd, dividerBottom)
            drawable?.draw(canvas)
        }
    }

    private fun Drawable?.getSaveIntrinsicHeight() : Int {
        return this?.intrinsicHeight ?: 0
    }

}
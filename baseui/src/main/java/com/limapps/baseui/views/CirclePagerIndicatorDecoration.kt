package com.limapps.baseui.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.limapps.baseui.R

abstract class CirclePagerIndicatorDecoration(context: Context, val marginTop: Int = 0) : RecyclerView.ItemDecoration() {
    @ColorInt
    protected var colorActive = ContextCompat.getColor(context, R.color.grey_bluey)

    @ColorInt
    protected var colorInactive = ContextCompat.getColor(context, R.color.grey_bluey_transparent)

    private val density = Resources.getSystem().displayMetrics.density

    protected val indicatorHeight = density * 8

    protected val indicatorItemWidth = density * 8

    protected val indicatorItemPadding = density * 6

    protected val interpolator = AccelerateDecelerateInterpolator()

    private val paint = Paint()

    init {
        paint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    fun getItemCount(recyclerView: RecyclerView) = recyclerView.adapter?.itemCount ?: 0

    abstract fun getPosition(parent: RecyclerView, itemCount: Int): PointF

    override fun onDrawOver(
            c: Canvas,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {
        super.onDrawOver(c, parent, state)

        val itemCount = getItemCount(parent)
        if (itemCount <= 1) return

        val position = getPosition(parent, itemCount)

        drawInactiveIndicators(c, position.x, position.y + marginTop, itemCount)

        // find active page (which should be highlighted)
        val layoutManager = parent.layoutManager as LinearLayoutManager
        val activePosition = layoutManager.findFirstVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        // find offset of active page (if the user is scrolling)
        val activeChild = layoutManager.findViewByPosition(activePosition)
        val left = activeChild!!.left
        val width = activeChild.width

        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        val progress = interpolator.getInterpolation(left * -1 / width.toFloat())

        drawHighlights(c, position.x, position.y + marginTop, activePosition, progress)
    }

    private fun drawInactiveIndicators(c: Canvas, indicatorStartX: Float, indicatorPosY: Float, itemCount: Int) {
        paint.color = colorInactive

        // width of item indicator including padding
        val itemWidth = indicatorItemWidth + indicatorItemPadding

        var start = indicatorStartX
        for (i in 0 until itemCount) {
            // draw the circle for every item
            c.drawCircle(start, indicatorPosY, indicatorItemWidth / 2, paint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
            c: Canvas,
            indicatorStartX: Float,
            indicatorPosY: Float,
            highlightPosition: Int,
            progress: Float
    ) {
        paint.color = colorActive

        // width of item indicator including padding
        val itemWidth = indicatorItemWidth + indicatorItemPadding

        if (progress == 0f) {
            // no swipe, draw a normal indicator
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            c.drawCircle(highlightStart, indicatorPosY,
                    indicatorItemWidth / 2, paint)
        } else {
            var highlightStart = indicatorStartX + itemWidth * highlightPosition
            // calculate partial highlight
            val partialLength = indicatorItemWidth * progress

            // draw the cut off highlight
            c.drawCircle(highlightStart + partialLength, indicatorPosY,
                    indicatorItemWidth / 2, paint)

        }
    }

    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = indicatorHeight.toInt()
    }
}
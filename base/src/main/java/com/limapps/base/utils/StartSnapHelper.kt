package com.limapps.base.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class StartSnapHelper : androidx.recyclerview.widget.LinearSnapHelper() {

    private var verticalHelper: androidx.recyclerview.widget.OrientationHelper? = null
    private var horizontalHelper: androidx.recyclerview.widget.OrientationHelper? = null

    @Throws(IllegalStateException::class)
    override fun attachToRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView?) {
        super.attachToRecyclerView(recyclerView)
    }

    override fun calculateDistanceToFinalSnap(layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager, targetView: View) =
            IntArray(2).apply {
                this[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager)).takeIf { layoutManager.canScrollHorizontally() } ?: 0
                this[1] = distanceToStart(targetView, getVerticalHelper(layoutManager)).takeIf { layoutManager.canScrollVertically() } ?: 0
            }

    override fun findSnapView(layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager) =
            super.findSnapView(layoutManager).takeUnless { layoutManager is androidx.recyclerview.widget.LinearLayoutManager }
                    ?: getStartView(layoutManager, getHorizontalHelper(layoutManager))?.takeIf { layoutManager.canScrollHorizontally() }
                    ?: getStartView(layoutManager, getVerticalHelper(layoutManager))

    private fun distanceToStart(targetView: View, helper: androidx.recyclerview.widget.OrientationHelper) = helper.getDecoratedStart(targetView) - helper.startAfterPadding

    private fun getStartView(layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager, helper: androidx.recyclerview.widget.OrientationHelper): View? {
        return if (layoutManager is androidx.recyclerview.widget.LinearLayoutManager) {
            val firstChild = layoutManager.findFirstVisibleItemPosition()
            val isLastItem = layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1

            if (firstChild == androidx.recyclerview.widget.RecyclerView.NO_POSITION || isLastItem) {
                return null
            }

            layoutManager.findViewByPosition(firstChild).takeIf { helper.getDecoratedEnd(it) >= helper.getDecoratedMeasurement(it) / 2 && helper.getDecoratedEnd(it) > 0 }
                    ?: layoutManager.findViewByPosition(firstChild + 1).takeUnless { layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1 }
        } else {
            super.findSnapView(layoutManager)
        }
    }

    private fun getVerticalHelper(layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager): androidx.recyclerview.widget.OrientationHelper {
        if (verticalHelper == null) {
            verticalHelper = androidx.recyclerview.widget.OrientationHelper.createVerticalHelper(layoutManager)
        }
        return verticalHelper!!
    }

    private fun getHorizontalHelper(layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager): androidx.recyclerview.widget.OrientationHelper {
        if (horizontalHelper == null) {
            horizontalHelper = androidx.recyclerview.widget.OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return horizontalHelper!!
    }
}
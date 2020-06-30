package com.limapps.base.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NoPredictiveLinearLayoutManager(context: Context?, orientation: Int = androidx.recyclerview.widget.RecyclerView.VERTICAL,
                                      reverseLayout: Boolean = false)
    : androidx.recyclerview.widget.LinearLayoutManager(context, orientation, reverseLayout) {
    /**
     * Disable predictive animations. There is a bug in RecyclerView which causes views that
     * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
     * adapter size has decreased since the ViewHolder was recycled.
     */
    override fun supportsPredictiveItemAnimations(): Boolean = false
}
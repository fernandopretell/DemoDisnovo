package com.limapps.base.utils

import androidx.constraintlayout.widget.Group
import android.view.View

fun Group.setOnClickListenerToViews(listener: View.OnClickListener?) {
    for (referencedId in referencedIds) {
        rootView.findViewById<View>(referencedId).setOnClickListener(listener)
    }
}
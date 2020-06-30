package com.limapps.base.uxcam

import android.view.View

interface UxCamObfuscator {
    fun occludeSensitiveViewWithoutGesture(view: View)
}
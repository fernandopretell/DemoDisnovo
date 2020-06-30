package com.limapps.base.others

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.load.Transformation

abstract class ImageOptions {

    abstract val url: String?

    open var resourcePlaceHolder: Int = -1

    open var drawablePlaceHolder: Drawable? = null

    open var width: Int = -1

    open var height: Int = -1

    open var transformation: Transformation<Bitmap>? = null

    open var useCenterInside: Boolean = false
}

package com.limapps.base.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat

fun resizeResourceIcon(context: Context, resource: Int, scale: Float = 1f): Bitmap {
    val bitmap = getBitmapForResource(context, resource)
    return resizeBitmap(bitmap, (bitmap.width * scale).toInt(), (bitmap.height * scale).toInt())
}

fun resizeBitmapIcon(context: Context, bitmap: Bitmap, resource: Int, scale: Float = 1f): Bitmap {
    val referenceBitmap = getBitmapForResource(context, resource)
    return resizeBitmapIcon(bitmap, referenceBitmap, scale)
}

fun resizeBitmapIcon(bitmap: Bitmap, referenceBitmap: Bitmap, scale: Float = 1f, shouldRecycle: Boolean = false): Bitmap {
    return resizeBitmap(bitmap, (referenceBitmap.width * scale).toInt(), (referenceBitmap.height * scale).toInt(), shouldRecycle)
}

fun getBitmapForResource(context: Context, resource: Int): Bitmap {
    val bitmapDrawable = ContextCompat.getDrawable(context, resource) as BitmapDrawable
    return bitmapDrawable.bitmap
}

fun resizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int, shouldRecycle: Boolean = false): Bitmap {

    val width = bitmap.width
    val height = bitmap.height

    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height

    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)

    val scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)

    if (shouldRecycle) {
        bitmap.recycle()
    }

    return scaledBitmap
}
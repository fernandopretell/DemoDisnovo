package com.limapps.base.others

import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.*

interface IResourceProvider {

    fun getString(@StringRes id: Int): String

    fun getString(@StringRes id: Int, vararg args: Any?): String

    fun getStringArray(@ArrayRes id: Int): Array<String>

    fun getQuantityString(@PluralsRes stringRes: Int, quantity: Int, vararg formatArgs: Any): String

    fun getFontTypeFace(@FontRes id: Int): Typeface?
    //TODO Eliminar estos metodos

    fun getColor(colorRes: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getIdentifier(name: String, defType: String): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getDrawable(drawableRes: Int): Drawable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun decodeResource(drawableRes: Int): Bitmap {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getBitmapDrawableFromBitmap(bitmap: Bitmap): BitmapDrawable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getDimensionPixelSize(dimenRes: Int): Int

    fun dpToPx(dp: Float): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
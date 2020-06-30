package com.limapps.base.others

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

@SuppressLint("StaticFieldLeak")
@Deprecated("Use an instance provided thought dagger (IResourceProvider)")
object ResourcesProvider : IResourceProvider {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    override fun getString(@StringRes id: Int): String = applicationContext.getString(id)

    override fun getString(@StringRes id: Int, vararg args: Any?): String = applicationContext.getString(id, *args)

    override fun getStringArray(@ArrayRes id: Int): Array<String> = applicationContext.resources.getStringArray(id)

    override fun getColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(applicationContext, colorRes)

    override fun getIdentifier(name: String, defType: String): Int =
            applicationContext.resources.getIdentifier(name, defType, applicationContext.packageName)

    override fun getDrawable(@DrawableRes drawableRes: Int): Drawable =
            requireNotNull(ContextCompat.getDrawable(applicationContext, drawableRes))

    override fun decodeResource(@DrawableRes drawableRes: Int): Bitmap =
            BitmapFactory.decodeResource(applicationContext.resources, drawableRes)

    override fun getBitmapDrawableFromBitmap(bitmap: Bitmap): BitmapDrawable =
            BitmapDrawable(applicationContext.resources, bitmap)

    override fun getQuantityString(@PluralsRes stringRes: Int, quantity: Int, vararg formatArgs: Any): String =
            applicationContext.resources.getQuantityString(stringRes, quantity, *formatArgs)

    override fun getDimensionPixelSize(@DimenRes dimenRes: Int): Int =
            applicationContext.resources.getDimensionPixelSize(dimenRes)

    override fun dpToPx(dp: Float): Int =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, applicationContext.resources.displayMetrics).toInt()

    override fun getFontTypeFace(id: Int): Typeface? = ResourcesCompat.getFont(applicationContext, id)
}
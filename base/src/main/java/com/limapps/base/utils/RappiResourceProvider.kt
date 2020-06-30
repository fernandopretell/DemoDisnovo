package com.limapps.base.utils

import android.app.Application
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.limapps.base.others.IResourceProvider
import dagger.Reusable

@Reusable
class RappiResourceProvider(private val app: Application) : IResourceProvider {

    override fun getString(id: Int, vararg args: Any?): String = app.getString(id, *args)

    override fun getStringArray(id: Int): Array<String> = app.resources.getStringArray(id)

    override fun getString(id: Int): String = app.getString(id)

    override fun getQuantityString(stringRes: Int, quantity: Int, vararg formatArgs: Any): String =
            app.resources.getQuantityString(stringRes, quantity, *formatArgs)

    override fun getFontTypeFace(id: Int): Typeface? = ResourcesCompat.getFont(app, id)

    override fun getDimensionPixelSize(dimenRes: Int): Int {
        return app.resources.getDimensionPixelSize(dimenRes)
    }
}
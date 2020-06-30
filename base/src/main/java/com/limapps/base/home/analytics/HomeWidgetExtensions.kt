package com.limapps.base.home.analytics

import com.limapps.base.home.HomeViewTypes
import com.limapps.base.home.HomeWidget
import java.util.Locale
import kotlin.collections.HashMap

// Analytics constants
const val KEY_WIDGET_CHECKOUT = "widget"
const val SOURCE_TYPE = "SOURCE_TYPE"
const val TITLE_OF_WIDGET = "TITLE_OF_WIDGET"
const val STYLE = "STYLE"
const val WIDGET_ID = "WIDGET_ID"
const val WIDGET_TYPE = "WIDGET_TYPE"
const val WIDGET_STYLE = "WIDGET_STYLE"

const val SOURCE_TYPE_APP_LIST = "APPLIST"
const val SOURCE_TYPE_STORE_DESCRIPTION = "STOREDESCRIPTION"
const val SOURCE_TYPE_STORE_INFO = "STOREINFO"
const val SOURCE_TYPE_PRODUCTS = "PRODUCTS"
const val SOURCE_TYPE_MAIN = "MAIN"
const val SOURCE_TYPE_MATRIX = "MATRIX"
const val SOURCE_TYPE_WIDGET = "WIDGET"


private val typeToStyle = mapOf(
        HomeViewTypes.APP_LIST.value to SOURCE_TYPE_APP_LIST,
        HomeViewTypes.STORE_DESCRIPTION.value to SOURCE_TYPE_STORE_DESCRIPTION,
        HomeViewTypes.STORES_STORE_INFO.value to SOURCE_TYPE_STORE_INFO,
        HomeViewTypes.PRODUCTS.value to SOURCE_TYPE_PRODUCTS,
        HomeViewTypes.MAIN.value to SOURCE_TYPE_MAIN,
        HomeViewTypes.MATRIX.value to SOURCE_TYPE_MATRIX
)

fun HomeWidget.toMap(): HashMap<String, String> {
    val typeString = type.value
    val source = when (type) {
        HomeViewTypes.MAIN -> SOURCE_TYPE_MAIN
        HomeViewTypes.MATRIX -> SOURCE_TYPE_MATRIX
        else -> SOURCE_TYPE_WIDGET
    }
    return hashMapOf(
            SOURCE_TYPE to source.toLowerCase(Locale.getDefault()),
            WIDGET_ID to id,
            WIDGET_TYPE to servicePaginatedType,
            WIDGET_STYLE to style,
            TITLE_OF_WIDGET to title,
            STYLE to (typeToStyle[typeString] ?: typeString).toLowerCase(Locale.getDefault())
    )
}

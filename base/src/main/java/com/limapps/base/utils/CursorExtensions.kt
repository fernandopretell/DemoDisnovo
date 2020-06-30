package com.limapps.base.utils

import android.database.Cursor

inline fun Cursor.iterateAndClose(action: (Cursor) -> Unit) {
    if (moveToFirst()) {
        do {
            action.invoke(this)
        } while (moveToNext())
    }
    close()
}

fun Cursor.getStringOrDefault(columnName: String, default: String = ""): String {
    val columnIndex = getColumnIndex(columnName)
    return when (isNull(columnIndex)) {
        true -> default
        else -> getString(columnIndex)
    }
}
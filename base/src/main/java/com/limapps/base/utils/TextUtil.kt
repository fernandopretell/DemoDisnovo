@file:JvmName("TextFunctions")

package com.limapps.base.utils

private const val WHIMS_PARTS = 2

fun getCleanWhimParam(text: String, index: Int, vararg whimSeparators: String): String? {
    var whimData = text
    if (text.isNotBlank()) {
        var whimArray: Array<String>
        for (i in whimSeparators.indices) {
            val whimSeparator = whimSeparators[i]
            whimArray = text.split(whimSeparator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (whimArray.size == WHIMS_PARTS) {
                whimData = whimArray[index].replace(whimSeparator.toRegex(), " ")
                break
            }
        }
    }
    return whimData
}

fun getEmojiByUnicode(unicode: Int) = String(Character.toChars(unicode))

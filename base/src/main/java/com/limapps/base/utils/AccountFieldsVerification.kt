package com.limapps.base.utils

fun String.strikeEmail(): String {
    val lastIndex = this.indexOf("@") - 2
    return if (lastIndex > 0) {
        "*".repeat(lastIndex).plus(this.substring(lastIndex))
    } else {
        this
    }
 }

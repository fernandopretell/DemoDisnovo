package com.limapps.base.utils

fun <T> List<T>.getElement(position: Int) =
        if (this.size > position)
            this[position]
        else
            null

package com.limapps.common

fun <T> List<T>.applyForEach(function: (T) -> Unit): List<T> = apply { forEach(function) }
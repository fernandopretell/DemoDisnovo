package com.limapps.base.helpers

fun Any?.toNonNullString() = this?.toString().orEmpty()

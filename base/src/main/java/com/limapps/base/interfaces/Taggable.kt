package com.limapps.base.interfaces

import android.os.Parcelable

interface Taggable : Parcelable {
    val id: Int
    val name: String
    val index: Int
}
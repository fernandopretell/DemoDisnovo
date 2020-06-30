package com.limapps.base.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDateTime

@Parcelize
data class LastScheduledTime(val storeId: Int = 0,
                             val openDate: LocalDateTime? = null,
                             val closeDate: LocalDateTime? = null) : Parcelable

fun LastScheduledTime.hasDates(): Boolean = openDate != null && closeDate != null
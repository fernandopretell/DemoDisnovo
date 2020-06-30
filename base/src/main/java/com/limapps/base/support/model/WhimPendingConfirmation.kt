package com.limapps.base.support.model

import android.os.Parcelable
import com.google.firebase.database.DataSnapshot
import com.limapps.common.tryOrDefault
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WhimPendingConfirmation(
        val id: Long = 0L,
        val name: String = "",
        val orderId: Long = 0L,
        val price: Double = 0.0,
        val state: String = "",
        val surcharge: Double = 0.0,
        val urlPhoto: String = ""
) : Parcelable {

    constructor(snapshot: DataSnapshot?) : this(
            tryOrDefault({
                snapshot?.child("id")?.getValue(Long::class.java) ?: 0L
            }, 0L),
            tryOrDefault({
                snapshot?.child("name")?.getValue(String::class.java) ?: ""
            }, ""),
            tryOrDefault({
                snapshot?.child("order_id")?.getValue(Long::class.java) ?: 0L
            }, 0L),
            tryOrDefault({ snapshot?.child("price")?.getValue(Double::class.java) }, null)
                    ?: tryOrDefault({
                        snapshot?.child("price")?.getValue(String::class.java)?.toDoubleOrNull()
                                ?: 0.0
                    }, 0.0),
            tryOrDefault({
                snapshot?.child("state")?.getValue(String::class.java) ?: ""
            }, ""),
            tryOrDefault({
                snapshot?.child("surcharge")?.getValue(Double::class.java) ?: 0.0
            }, 0.0),
            tryOrDefault({
                snapshot?.child("url_photo")?.getValue(String::class.java) ?: ""
            }, "")
    )
}
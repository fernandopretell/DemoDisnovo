package com.limapps.base.firebase.adapters

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

interface ValueEventListenerAdapter : ValueEventListener {
    override fun onCancelled(databaseError: DatabaseError) {}

    override fun onDataChange(dataSnapshot: DataSnapshot) {}
}

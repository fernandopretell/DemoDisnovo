package com.limapps.base.persistence.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.limapps.common.tryOrPrintException

class ConnectionHelper(context: Context, private val connection: SettingsConnection)
    : SQLiteOpenHelper(context, connection.nameDataBase, null, connection.versionDataBase) {

    override fun onCreate(db: SQLiteDatabase) {
        connection.createQueries.forEach {
            try {
                db.execSQL(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        connection.getUpgradeQueries().forEach { tryOrPrintException { db.execSQL(it) } }
    }
}
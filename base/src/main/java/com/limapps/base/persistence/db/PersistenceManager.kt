package com.limapps.base.persistence.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

object PersistenceManager {

    private lateinit var connectionHelper: ConnectionHelper

    @Synchronized
    fun createDatabase(settingsConnection: SettingsConnection, context: Context) {
        connectionHelper = ConnectionHelper(context, settingsConnection)
    }

    @Synchronized
    fun get(): SQLiteDatabase = connectionHelper.writableDatabase

    fun rawQuery(sql: String, selectionArgs: Array<String> = emptyArray()): Cursor = get().rawQuery(sql, selectionArgs)

    fun execMainSql(sql: String, params: Array<String> = emptyArray()) = get().execSQL(sql, params)

    fun insertOrUpdate(tableName: String, values: ContentValues): Long? {
        return get().insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun deleteFromDB(tableName: String, where: String = "", whereArgs: Array<String> = emptyArray()): Int? {
        return get().delete(tableName, where, whereArgs)
    }
}

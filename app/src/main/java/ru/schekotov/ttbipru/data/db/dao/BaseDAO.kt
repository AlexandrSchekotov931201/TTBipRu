package ru.schekotov.ttbipru.data.db.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import ru.schekotov.ttbipru.data.db.DBHelper

abstract class BaseDAO(protected val dbHelper: DBHelper) {

    protected fun closeDbWithEndTransaction(db: SQLiteDatabase?) {
        if (db != null && db.isOpen) {
            try {
                if (db.inTransaction()) {
                    db.endTransaction()
                }
            } catch (e: SQLiteException) {
                Log.e(TAG, e.message!!)
            }
            closeDB(db)
        }
    }

    private fun closeDB(db: SQLiteDatabase?) {
        if (db != null && db.isOpen) {
            db.close()
        }
    }

    protected fun closeCursor(cursor: Cursor?) {
        cursor?.close()
    }

    companion object {
        private const val TAG = "BaseDAO"
    }

}
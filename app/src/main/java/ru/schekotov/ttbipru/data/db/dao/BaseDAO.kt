package ru.schekotov.ttbipru.data.db.dao

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import ru.schekotov.ttbipru.data.db.DBHelper

/**
 * Базовый класс для объектов доступа к данным в БД
 *
 * @author Щёкотов Александр
 */
abstract class BaseDAO(protected val dbHelper: DBHelper) {

    /** закрыть соединение с БД и завершить транзакцию */
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

    /** закрыть соединение с БД */
    private fun closeDB(db: SQLiteDatabase?) {
        if (db != null && db.isOpen) {
            db.close()
        }
    }

    companion object {
        private const val TAG = "BaseDAO"
    }

}
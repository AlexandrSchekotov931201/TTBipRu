package ru.schekotov.ttbipru.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.schekotov.ttbipru.data.db.entity.SQL_CREATE_VEHICLE_TABLE
import ru.schekotov.ttbipru.data.db.entity.SQL_DROP__VEHICLE_TABLE

/**
 * Вспомогательный класс для работы с БД
 *
 * @author Щёкотов Александр
 */
class DBHelper(
    context: Context,
    dbName: String = "TTBip.ru.db",
    version: Int = 1
) : SQLiteOpenHelper(
    context,
    dbName,
    null,
    version
) {

    override fun onCreate(db: SQLiteDatabase) {
        createEmptyTables(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        dropMessengerTables(db)
        createEmptyTables(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        dropMessengerTables(db)
        createEmptyTables(db)
    }

    /** создать пустые таблицы в БД */
    fun createEmptyTables(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_VEHICLE_TABLE)
    }

    /** удалить таблицы в БД */
    fun dropMessengerTables(db: SQLiteDatabase) {
        db.execSQL(SQL_DROP__VEHICLE_TABLE)
    }
}
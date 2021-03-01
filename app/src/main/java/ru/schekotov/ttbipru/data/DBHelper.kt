package ru.schekotov.ttbipru.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.schekotov.ttbipru.data.entity.SQL_CREATE_RATING_CSI_TABLE
import ru.schekotov.ttbipru.data.entity.SQL_DROP_RATING_CSI_TABLE

class DBHelper(context: Context, val dbName: String = "TTBip.ru.db", val version: Int = 1) :
    SQLiteOpenHelper(context, dbName, null, version) {

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

    fun asd() : String {
        return "Все получилось!"
    }

    private fun createEmptyTables(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_RATING_CSI_TABLE)
    }

    private fun dropMessengerTables(db: SQLiteDatabase) {
        db.execSQL(SQL_DROP_RATING_CSI_TABLE)
    }
}
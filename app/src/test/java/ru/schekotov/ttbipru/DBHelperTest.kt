package ru.schekotov.ttbipru

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.schekotov.ttbipru.data.db.DBHelper
import ru.schekotov.ttbipru.data.db.entity.SQL_CREATE_VEHICLE_TABLE
import ru.schekotov.ttbipru.data.db.entity.SQL_DROP__VEHICLE_TABLE

/**
 * Тест класса [DBHelper]
 *
 * @author Щёкотов Александр
 */
class DBHelperTest {

    private val contextMock: Context = mockk()
    private val helper = DBHelper(contextMock)
    private val db: SQLiteDatabase = mockk()

    @Before
    fun setUp() {
        every { db.execSQL(any()) }.returns(Unit)
    }

    @Test
    fun onCreate() {
        helper.onCreate(db)
        verifyCreateEmptyTables()
    }

    @Test
    fun onUpgrade() {
        helper.onUpgrade(db, 1, 2)
        verifyDropMessengerTables()
        verifyCreateEmptyTables()
    }

    @Test
    fun onDowngrade() {
        helper.onDowngrade(db, 2, 1)
        verifyDropMessengerTables()
        verifyCreateEmptyTables()
    }

    @Test
    fun createEmptyTables() {
        helper.createEmptyTables(db)
        verifyCreateEmptyTables()
    }

    @Test
    fun dropMessengerTables() {
        helper.dropMessengerTables(db)
        verifyDropMessengerTables()
    }

    private fun verifyCreateEmptyTables() {
        verify { db.execSQL(SQL_CREATE_VEHICLE_TABLE) }
    }

    private fun verifyDropMessengerTables() {
        verify { db.execSQL(SQL_DROP__VEHICLE_TABLE) }
    }

}
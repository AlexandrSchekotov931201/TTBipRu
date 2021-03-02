package ru.schekotov.ttbipru

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.schekotov.ttbipru.data.db.DBHelper
import ru.schekotov.ttbipru.data.db.dao.VehicleDAO
import ru.schekotov.ttbipru.data.model.VehicleModel

/**
 * Тест класса [VehicleDAO]
 *
 * @author Щёкотов Александр
 */
@RunWith(RobolectricTestRunner::class)
class VehicleDAOTest {

    private var dbHelper: DBHelper = DBHelper(ApplicationProvider.getApplicationContext())
    private var vehicleDAO: VehicleDAO = VehicleDAO(dbHelper)
    private lateinit var vehicleModel: VehicleModel

    @Before
    fun before() {
        vehicleModel = VehicleModel(
            "A000AA 77",
            "A000AA 78",
            "A000AA 79")
    }

    @Test
    fun insertAndGetVehicleTest() {
        val expectedVehicleModel = VehicleModel(
            "A000AA 77",
            "A000AA 78",
            "A000AA 79")
        vehicleDAO.insertVehicle(vehicleModel)
        assertEquals(expectedVehicleModel, vehicleDAO.getVehicle())
    }

}
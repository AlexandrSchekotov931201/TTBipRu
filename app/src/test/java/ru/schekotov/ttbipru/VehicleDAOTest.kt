package ru.schekotov.ttbipru

import androidx.test.core.app.ApplicationProvider
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.schekotov.ttbipru.data.db.DBHelper
import ru.schekotov.ttbipru.data.db.dao.impl.VehicleDAO
import ru.schekotov.ttbipru.data.db.dao.interfaces.IVehicleDAO
import ru.schekotov.ttbipru.data.model.VehicleModel

/**
 * Тест класса [VehicleDAO]
 *
 * @author Щёкотов Александр
 */
@RunWith(RobolectricTestRunner::class)
class VehicleDAOTest {

    private var dbHelper: DBHelper = DBHelper(ApplicationProvider.getApplicationContext())
    private var vehicleDAO: IVehicleDAO = VehicleDAO(dbHelper)
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
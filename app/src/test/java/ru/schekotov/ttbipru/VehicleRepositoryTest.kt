package ru.schekotov.ttbipru

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.schekotov.ttbipru.data.db.dao.interfaces.IVehicleDAO
import ru.schekotov.ttbipru.data.model.VehicleModel
import ru.schekotov.ttbipru.data.repositorys.IVehicleRepository
import ru.schekotov.ttbipru.data.repositorys.impl.VehicleRepository

/**
 * Тест класса [VehicleRepository]
 *
 * @author Щёкотов Александр
 */
class VehicleRepositoryTest {

    private var vehicleDAO: IVehicleDAO = mockk(relaxed = true)
    private var vehicleRepository: IVehicleRepository = mockk()
    private lateinit var vehicleModel: VehicleModel

    @Before
    fun before() {
        vehicleRepository = VehicleRepository(vehicleDAO)
        vehicleModel = VehicleModel(
            "A000AA 77",
            "A000AA 78",
            "A000AA 79")
    }

    @Test
    fun insertVehicleTest() {
        vehicleRepository.insertVehicle(vehicleModel)
        verify { vehicleDAO.insertVehicle(vehicleModel) }
    }

    @Test
    fun getVehicleTest() {
        every { vehicleDAO.getVehicle() } returns vehicleModel
        val vehicle = vehicleRepository.getVehicle()
        verify { vehicleDAO.getVehicle() }
        assertEquals(vehicleModel, vehicle)
    }

}
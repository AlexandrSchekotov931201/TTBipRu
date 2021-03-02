package ru.schekotov.ttbipru

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.schekotov.ttbipru.data.model.VehicleModel
import ru.schekotov.ttbipru.data.repositorys.IVehicleRepository
import ru.schekotov.ttbipru.domain.IVehicleInteractor
import ru.schekotov.ttbipru.domain.impl.VehicleInteractor

/**
 * Тест класса [VehicleInteractor]
 *
 * @author Щёкотов Александр
 */
class VehicleInteractorTest {

    private var vehicleRepository: IVehicleRepository = mockk(relaxed = true)
    private var vehicleInteractor: IVehicleInteractor = mockk()
    private lateinit var vehicleModel: VehicleModel

    @Before
    fun before() {
        vehicleInteractor = VehicleInteractor(vehicleRepository)
        vehicleModel = VehicleModel(
            "A000AA 77",
            "A000AA 78",
            "A000AA 79")
    }

    @Test
    fun insertVehicleTest() {
        vehicleInteractor.insertVehicle(vehicleModel)
        verify { vehicleInteractor.insertVehicle(vehicleModel) }
    }

    @Test
    fun getVehicleTest() {
        every { vehicleRepository.getVehicle() } returns vehicleModel
        val vehicle = vehicleInteractor.getVehicle()
        verify { vehicleInteractor.getVehicle() }
        assertEquals(vehicleModel, vehicle)
    }

}
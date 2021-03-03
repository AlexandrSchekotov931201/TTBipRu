package ru.schekotov.ttbipru.data.repositorys.impl

import ru.schekotov.ttbipru.data.db.dao.interfaces.IVehicleDAO
import ru.schekotov.ttbipru.data.model.VehicleModel
import ru.schekotov.ttbipru.data.repositorys.IVehicleRepository

/**
 * Репозиторий для работы с данными по ТС
 *
 * @author Щёкотов Александр
 */
class VehicleRepository(private val dao: IVehicleDAO) : IVehicleRepository {

    override fun insertVehicle(vehicle: VehicleModel) {
        dao.insertVehicle(vehicle)
    }

    override fun getVehicle() : VehicleModel{
        return dao.getVehicle()
    }

}
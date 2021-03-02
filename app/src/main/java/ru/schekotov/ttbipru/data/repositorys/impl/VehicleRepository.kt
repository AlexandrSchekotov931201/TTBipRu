package ru.schekotov.ttbipru.data.repositorys.impl

import ru.schekotov.ttbipru.data.db.dao.VehicleDAO
import ru.schekotov.ttbipru.data.model.VehicleModel
import ru.schekotov.ttbipru.data.repositorys.IVehicleRepository

class VehicleRepository(private val dao: VehicleDAO) : IVehicleRepository {

    override fun insertVehicle(vehicle: VehicleModel) {
        dao.insertVehicle(vehicle)
    }

    override fun getVehicle() : VehicleModel{
        return dao.getVehicle()
    }

}
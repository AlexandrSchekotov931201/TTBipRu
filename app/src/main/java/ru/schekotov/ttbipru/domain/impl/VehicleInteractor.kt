package ru.schekotov.ttbipru.domain.impl

import ru.schekotov.ttbipru.data.model.VehicleModel
import ru.schekotov.ttbipru.data.repositorys.IVehicleRepository
import ru.schekotov.ttbipru.domain.IVehicleInteractor

/**
 * Интерактор для работы с данными по ТС
 *
 * @author Щёкотов Александр
 */
class VehicleInteractor(private val vehicleRepository: IVehicleRepository) : IVehicleInteractor{

    override fun insertVehicle(vehicle: VehicleModel) {
        vehicleRepository.insertVehicle(vehicle)
    }

    override fun getVehicle(): VehicleModel {
        return vehicleRepository.getVehicle()
    }

}
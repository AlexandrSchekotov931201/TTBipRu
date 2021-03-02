package ru.schekotov.ttbipru.data.repositorys

import ru.schekotov.ttbipru.data.model.VehicleModel

interface IVehicleRepository {

    fun insertVehicle(vehicle: VehicleModel)

    fun getVehicle() : VehicleModel

}
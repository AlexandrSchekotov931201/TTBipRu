package ru.schekotov.ttbipru.domain

import ru.schekotov.ttbipru.data.model.VehicleModel

interface IVehicleInteractor {

    fun insertVehicle(vehicle: VehicleModel)

    fun getVehicle() : VehicleModel

}
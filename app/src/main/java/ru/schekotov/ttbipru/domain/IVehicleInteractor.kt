package ru.schekotov.ttbipru.domain

import ru.schekotov.ttbipru.data.model.VehicleModel

/**
 * Интерфейс Интерактора для работы с данными по ТС
 *
 * @author Щёкотов Александр
 */
interface IVehicleInteractor {

    fun insertVehicle(vehicle: VehicleModel)

    fun getVehicle() : VehicleModel

}
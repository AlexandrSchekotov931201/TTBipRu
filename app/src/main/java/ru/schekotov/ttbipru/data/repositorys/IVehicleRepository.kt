package ru.schekotov.ttbipru.data.repositorys

import ru.schekotov.ttbipru.data.model.VehicleModel

/**
 * Интерфейс репозитория для работы с данными по ТС
 *
 * @author Щёкотов Александр
 */
interface IVehicleRepository {

    fun insertVehicle(vehicle: VehicleModel)

    fun getVehicle() : VehicleModel

}
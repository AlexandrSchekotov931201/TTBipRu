package ru.schekotov.ttbipru.data.repositorys

import ru.schekotov.ttbipru.data.model.VehicleModel

/**
 * Интерфейс репозитория для работы с данными по ТС
 *
 * @author Щёкотов Александр
 */
interface IVehicleRepository {

    /** Добавить информацию по транспортному средству
     *
     * @param vehicle модель данных которая хранит в себе информацию о ТС
     */
    fun insertVehicle(vehicle: VehicleModel)

    /** Получить информацию по транспортному средству */
    fun getVehicle() : VehicleModel

}
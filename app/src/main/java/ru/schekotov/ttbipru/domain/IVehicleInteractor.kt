package ru.schekotov.ttbipru.domain

import ru.schekotov.ttbipru.data.model.VehicleModel

/**
 * Интерфейс Интерактора для работы с данными по ТС
 *
 * @author Щёкотов Александр
 */
interface IVehicleInteractor {

    /** Добавить информацию по транспортному средству
     *
     * @param vehicle модель данных которая хранит в себе информацию о ТС
     */
    fun insertVehicle(vehicle: VehicleModel)

    /** Получить информацию по транспортному средству */
    fun getVehicle() : VehicleModel

}
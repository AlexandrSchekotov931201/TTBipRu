package ru.schekotov.ttbipru.data.db.dao.interfaces

import ru.schekotov.ttbipru.data.model.VehicleModel

/**
 * Интерфейс объекта доступа к данным по транспортному средству в БД
 *
 * @author Щёкотов Александр
 */
interface IVehicleDAO {

    /** Добавить информацию по транспортному средству
     *
     * @param vehicle модель данных которая хранит в себе информацию о ТС
     */
    fun insertVehicle(vehicle: VehicleModel)

    /** Получить информацию по транспортному средству */
    fun getVehicle(): VehicleModel

}
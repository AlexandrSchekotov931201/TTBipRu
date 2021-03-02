package ru.schekotov.ttbipru.data.db.entity

import android.provider.BaseColumns

/**
 * Описание структуры и свойств таблицы c информацией о транспортном средстве
 *
 * @author Щёкотов Алекснадр
 */

/**
 * SQL запрос создания таблицы vehicle
 */
const val SQL_CREATE_VEHICLE_TABLE = """
    CREATE TABLE ${VehicleEntry.VEHICLE_TABLE_NAME} (
        ${VehicleEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${VehicleEntry.COLUMN_VEHICLE_NUMBER} TEXT,
        ${VehicleEntry.COLUMN_REGISTRATION_CERTIFICATE} TEXT,
        ${VehicleEntry.COLUMN_LICENSE_NUMBER} TEXT
    )"""

/**
 * SQL запрос удаления таблицы vehicle
 */
const val SQL_DROP__VEHICLE_TABLE = "DROP TABLE IF EXISTS " + VehicleEntry.VEHICLE_TABLE_NAME

/**
 * SQL запрос очистки всех строк таблицы vehicle
 */
const val SQL_DELETE_VEHICLE_TABLE = "delete from " + VehicleEntry.VEHICLE_TABLE_NAME

/**
 * Описание структуры таблицы в БД
 */
object VehicleEntry : BaseColumns {
    const val VEHICLE_TABLE_NAME = "vehicle"
    const val COLUMN_ID = "id"
    const val COLUMN_VEHICLE_NUMBER = "vehicle_number"
    const val COLUMN_REGISTRATION_CERTIFICATE = "vehicle_registration_certificate"
    const val COLUMN_LICENSE_NUMBER = "drivers_license_number"
}
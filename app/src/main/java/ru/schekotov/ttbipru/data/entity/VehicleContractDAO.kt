package ru.schekotov.ttbipru.data.entity

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
    CREATE TABLE ${NotesEntry.TABLE_NAME} (
        ${NotesEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${NotesEntry.COLUMN_TITLE_NOTE} TEXT,
        ${NotesEntry.COLUMN_TEXT_NOTE} TEXT,
        ${NotesEntry.COLUMN_DATE_NOTE} INTEGER
    )"""

/**
 * SQL запрос удаления таблицы vehicle
 */
const val SQL_DROP__VEHICLE_TABLE = "DROP TABLE IF EXISTS " + VehicleEntry.TABLE_NAME

/**
 * SQL запрос очистки всех строк таблицы vehicle
 */
const val SQL_DELETE__VEHICLE_TABLE = "delete from " + VehicleEntry.TABLE_NAME

/**
 * Описание структуры таблицы в БД
 */
object VehicleEntry : BaseColumns {
    const val TABLE_NAME = "vehicle"
    const val COLUMN_ID = "id"
    const val COLUMN_TITLE_NOTE = "vehicle_number"
    const val COLUMN_TEXT_NOTE = "vehicle_registration_certificate"
    const val COLUMN_DATE_NOTE = "drivers_license_number"
}
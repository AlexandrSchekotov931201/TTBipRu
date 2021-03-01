package ru.schekotov.ttbipru.data.entity

import android.provider.BaseColumns

/**
 * Описание структуры и свойств таблицы c информацией о заметках
 *
 * @author Schekotov
 */

/**
 * SQL запрос создания таблицы notes
 */
const val SQL_CREATE_RATING_CSI_TABLE = """
    CREATE TABLE ${NotesEntry.TABLE_NAME} (
        ${NotesEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${NotesEntry.COLUMN_TITLE_NOTE} TEXT,
        ${NotesEntry.COLUMN_TEXT_NOTE} TEXT,
        ${NotesEntry.COLUMN_DATE_NOTE} INTEGER
    )"""

/**
 * SQL запрос удаления таблицы notes
 */
const val SQL_DROP_RATING_CSI_TABLE = "DROP TABLE IF EXISTS " + NotesEntry.TABLE_NAME

/**
 * SQL запрос очистки всех строк таблицы notes
 */
const val SQL_DELETE_RATING_CSI_TABLE = "delete from " + NotesEntry.TABLE_NAME

/**
 * Описание структуры таблицы в БД
 */
object NotesEntry : BaseColumns {
    const val TABLE_NAME = "notes"
    const val COLUMN_ID = "id"
    const val COLUMN_TITLE_NOTE = "title_note"
    const val COLUMN_TEXT_NOTE = "text_note"
    const val COLUMN_DATE_NOTE = "date_note"
}
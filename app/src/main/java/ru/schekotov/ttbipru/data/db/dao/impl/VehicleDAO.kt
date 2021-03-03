package ru.schekotov.ttbipru.data.db.dao.impl

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import ru.schekotov.ttbipru.data.db.DBHelper
import ru.schekotov.ttbipru.data.db.dao.BaseDAO
import ru.schekotov.ttbipru.data.db.dao.interfaces.IVehicleDAO
import ru.schekotov.ttbipru.data.model.VehicleModel
import ru.schekotov.ttbipru.data.db.entity.VehicleEntry.COLUMN_LICENSE_NUMBER
import ru.schekotov.ttbipru.data.db.entity.VehicleEntry.COLUMN_REGISTRATION_CERTIFICATE
import ru.schekotov.ttbipru.data.db.entity.VehicleEntry.COLUMN_VEHICLE_NUMBER
import ru.schekotov.ttbipru.data.db.entity.VehicleEntry.VEHICLE_TABLE_NAME

/**
 * Объект доступа к данным по транспортному средству в БД
 *
 * @author Щёкотов Александр
 */
class VehicleDAO(dbHelper: DBHelper) : BaseDAO(dbHelper), IVehicleDAO {

    @Synchronized
    override fun insertVehicle(vehicle: VehicleModel) {
        val contentValues: ContentValues
        var db: SQLiteDatabase? = null
        try {
            db = dbHelper.writableDatabase
            contentValues = ContentValues()
            contentValues.put(COLUMN_VEHICLE_NUMBER, vehicle.vehicleNumber)
            contentValues.put(COLUMN_REGISTRATION_CERTIFICATE, vehicle.vehicleRegistrationCertificate)
            contentValues.put(COLUMN_LICENSE_NUMBER, vehicle.driversLicenseNumber)
            db.delete(VEHICLE_TABLE_NAME, null, null)
            db.insert(VEHICLE_TABLE_NAME, null, contentValues)
        } catch (e: SQLiteException) {
            Log.e(TAG, e.message!!)
        } finally {
            closeDbWithEndTransaction(db)
        }
    }

    @Synchronized
    override fun getVehicle(): VehicleModel {
        var db: SQLiteDatabase? = null
        var vehicle = VehicleModel()
        try {
            db = dbHelper.writableDatabase
            val cursor = db.query(VEHICLE_TABLE_NAME, null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                val vehicleNumberColIndex = cursor.getColumnIndex(COLUMN_VEHICLE_NUMBER)
                val vehicleRegistrationCertificateColIndex = cursor.getColumnIndex(COLUMN_REGISTRATION_CERTIFICATE)
                val driversLicenseNumberColIndex = cursor.getColumnIndex(COLUMN_LICENSE_NUMBER)
                do {
                    val vehicleNumber = cursor.getString(vehicleNumberColIndex)
                    val vehicleRegistrationCertificate = cursor.getString(vehicleRegistrationCertificateColIndex)
                    val driversLicenseNumber = cursor.getString(driversLicenseNumberColIndex)
                    vehicle = VehicleModel(
                        vehicleNumber,
                        vehicleRegistrationCertificate,
                        driversLicenseNumber
                    )
                } while (cursor.moveToNext())
                return vehicle
            }
        } catch (e: SQLiteException) {
            Log.e(TAG, e.message!!)
        } finally {
            closeDbWithEndTransaction(db)
        }
        return vehicle
    }

    companion object {
        private const val TAG = "VehicleDAO"
    }
}
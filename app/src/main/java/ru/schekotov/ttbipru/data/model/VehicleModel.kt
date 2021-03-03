package ru.schekotov.ttbipru.data.model

/**
 * Модель данных которая хранит в себе информацию о ТС
 *
 * @property vehicleNumber номер ТС
 * @property vehicleRegistrationCertificate номер СТС
 * @property driversLicenseNumber номер ВУ
 *
 * @author Щёкотов Александр
 */
data class VehicleModel(
    var vehicleNumber: String = "",
    var vehicleRegistrationCertificate: String = "",
    var driversLicenseNumber: String = ""
)
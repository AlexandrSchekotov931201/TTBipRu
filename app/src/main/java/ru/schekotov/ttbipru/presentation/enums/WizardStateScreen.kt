package ru.schekotov.ttbipru.presentation.enums

import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.presentation.model.WizardContentModel

/**
 * Перечесление состояний экранов визарда
 *
 * @property state модель данных которая хранит в себе информацию о контенте визард экрана
 * @author Щёкотова Александр
 */
enum class WizardStateScreen(val state: WizardContentModel) {

    /** экран ввода регистрационного номера ТС" */
    VEHICLE_NUMBER(
        WizardContentModel(
            R.string.vehicle_number_title,
            R.drawable.vehicle_number
        )
    ),

    /** экран ввода номера свидетельства о регистрации ТС" */
    REGISTRATION_CERTIFICATE_NUMBER(
        WizardContentModel(
            R.string.registration_certificate_number_title,
            R.drawable.registration_certification
        )
    ),

    /** экран ввода номера водительского удостоверения" */
    DRIVERS_LICENSE_NUMBER(
        WizardContentModel(
            R.string.drivers_license_number_title,
            R.drawable.drivers_license
        )
    )
}
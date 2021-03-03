package ru.schekotov.ttbipru.presentation.enums

import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.presentation.model.WizardContentModel

/**
 * Перечесление состояний экранов визарда
 *
 * @author Щёкотова Александр
 */
enum class WizardStateScreen(val state: WizardContentModel) {
    VEHICLE_NUMBER(WizardContentModel(R.string.vehicle_number_title, R.drawable.vehicle_number)),
    REGISTRATION_CERTIFICATE_NUMBER(WizardContentModel(R.string.registration_certificate_number_title, R.drawable.registration_certification)),
    DRIVERS_LICENSE_NUMBER(WizardContentModel(R.string.drivers_license_number_title, R.drawable.drivers_license))
}
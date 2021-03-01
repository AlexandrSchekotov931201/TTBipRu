package ru.schekotov.ttbipru.enums

import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.presentation.model.WizardContentModel

enum class WizardStateScreen(val state: WizardContentModel) {
    VEHICLE_NUMBER(WizardContentModel(R.string.vehicle_number_title)),
    REGISTRATION_CERTIFICATE_NUMBER(WizardContentModel(R.string.registration_certificate_number_title)),
    DRIVERS_LICENSE_NUMBER(WizardContentModel(R.string.drivers_license_number_title))
}
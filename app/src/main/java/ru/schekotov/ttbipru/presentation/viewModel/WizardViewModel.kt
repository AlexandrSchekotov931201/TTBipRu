package ru.schekotov.ttbipru.presentation.viewModel

import androidx.lifecycle.ViewModel
import ru.schekotov.ttbipru.data.model.VehicleModel
import ru.schekotov.ttbipru.domain.IVehicleInteractor

class WizardViewModel(private val vehicleInteractor: IVehicleInteractor) : ViewModel() {

    fun insertData(vehicleModel: VehicleModel) {
        vehicleInteractor.insertVehicle(vehicleModel)
    }

    fun getData() : VehicleModel {
        return vehicleInteractor.getVehicle()
    }

}
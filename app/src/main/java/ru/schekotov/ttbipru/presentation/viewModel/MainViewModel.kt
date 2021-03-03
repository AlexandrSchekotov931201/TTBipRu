package ru.schekotov.ttbipru.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.schekotov.ttbipru.data.model.VehicleModel
import ru.schekotov.ttbipru.domain.IVehicleInteractor

/**
 * ViewModel для работы с главным экраном
 *
 * @property vehicleInteractor интерактор для работы с данными по ТС
 *
 * @author Щёкотов Александр
 */
class MainViewModel(
    private val vehicleInteractor: IVehicleInteractor
) : ViewModel() {

    private val displayVehicleDataLiveDate: MutableLiveData<VehicleModel> = MutableLiveData()

    /** Произошло событие отображения индикатора с точками */
    fun onDisplayVehicleData() {
        displayVehicleDataLiveDate.value = vehicleInteractor.getVehicle()
    }

    /** Получить LiveData для отображения данных на главном экране */
    fun getVehicleLiveDate(): LiveData<VehicleModel> {
        return displayVehicleDataLiveDate
    }
}
package ru.schekotov.ttbipru.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.schekotov.ttbipru.data.model.VehicleModel
import ru.schekotov.ttbipru.domain.IVehicleInteractor
import ru.schekotov.ttbipru.presentation.enums.WizardStateScreen

/**
 * ViewModel для работы с экранами визарда
 *
 * @property vehicleInteractor интерактор для работы с данными по ТС
 * @property currentWizardStateScreen текущее состояние экрана визарда
 * @property vehicleMap структура данных необходимая для сбора заполненный полей и преоброзования их в модельданных
 *
 * @author Щёкотов Александр
 */
class WizardViewModel(
    private val vehicleInteractor: IVehicleInteractor,
    private val currentWizardStateScreen: WizardStateScreen,
    private val vehicleMap: HashMap<WizardStateScreen, String>
) : ViewModel() {

    private val nextWizardStateScreen: WizardStateScreen = getNextWizardStateScreenFromCurrent()
    private val wizardStateScreenLiveDate: MutableLiveData<WizardStateScreen> = MutableLiveData()
    private val errorFieldLiveDate: MutableLiveData<Boolean> = MutableLiveData()

    /** Произошло событие перехода на следующий экран визарда */
    fun onWizardNext() {
        wizardStateScreenLiveDate.value = currentWizardStateScreen
    }

    /** Произошло событие положительного заполнения поля ввода визарда */
    fun onGoodField() {
        errorFieldLiveDate.value = false
    }

    /** Произошло событие ошибки заполнения поля ввода визарда */
    fun onErrorField() {
        errorFieldLiveDate.value = true
    }

    /** Произошло событие добавление данных по ТС, ВУ и СТС*/
    fun onInsertData() {
        vehicleInteractor.insertVehicle(getVehicleModel())
    }

    /** Получить текущее состояние визарда */
    fun getCurrentWizardStateScreen() : WizardStateScreen {
        return currentWizardStateScreen
    }

    /** Получить следующее состояние визарда */
    fun getNextWizardStateScreen() : WizardStateScreen {
        return nextWizardStateScreen
    }

    /** Получить карту по заполненными данными по ТС, ВУ и СТС */
    fun getVehicleMap() : HashMap<WizardStateScreen, String> {
        return vehicleMap
    }

    /** Получить LiveData состояния экрана визарда */
    fun getWizardStateScreenLiveDate(): LiveData<WizardStateScreen> {
        return wizardStateScreenLiveDate
    }

    /** Получить LiveData состояния ошибки поля ввода визарда */
    fun getErrorFieldLiveDateLiveDate(): LiveData<Boolean> {
        return errorFieldLiveDate
    }

    /** Получить модель данных с ТС, ВУ и СТС*/
    private fun getVehicleModel() : VehicleModel{
        val vehicleNumber = vehicleMap[WizardStateScreen.VEHICLE_NUMBER]
        val vehicleModel = VehicleModel()
        val vehicleRegistrationCertificate = vehicleMap[WizardStateScreen.REGISTRATION_CERTIFICATE_NUMBER]
        val driversLicenseNumber = vehicleMap[WizardStateScreen.DRIVERS_LICENSE_NUMBER]

        if (vehicleNumber != null) vehicleModel.vehicleNumber = vehicleNumber
        if (vehicleRegistrationCertificate != null) vehicleModel.vehicleRegistrationCertificate = vehicleRegistrationCertificate
        if (driversLicenseNumber != null) vehicleModel.driversLicenseNumber = driversLicenseNumber

        return vehicleModel
    }

    /** Получить следующее состояния экрана визарда из текущего */
    private fun getNextWizardStateScreenFromCurrent(): WizardStateScreen {
        val wizardStateScreens = WizardStateScreen.values()
        for (index in wizardStateScreens.indices) {
            if (currentWizardStateScreen == wizardStateScreens[index] && index < wizardStateScreens.size - 1) {
                return wizardStateScreens[index + 1]
            }
        }
        return WizardStateScreen.VEHICLE_NUMBER
    }
}
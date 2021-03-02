package ru.schekotov.ttbipru.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import ru.schekotov.ttbipru.App
import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.APP_PREFERENCES
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.IS_VISIBLE_WALK_THROUGH_SCREEN_ACTIVITY_KEY
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.IS_VISIBLE_WIZARD_SCREEN_KEY
import ru.schekotov.ttbipru.data.model.VehicleModel
import ru.schekotov.ttbipru.domain.IVehicleInteractor
import ru.schekotov.ttbipru.enums.WizardStateScreen
import ru.schekotov.ttbipru.presentation.ViewModelProviderFactory
import ru.schekotov.ttbipru.presentation.viewModel.MainViewModel
import javax.inject.Inject

/**
 * Активити главного экрана
 *
 * @author Щёкотов Александр
 */
class MainActivity : AppCompatActivity() {

    private lateinit var vehicleNumberTextView: EditText
    private lateinit var vehicleRegistrationCertificateTextView: EditText
    private lateinit var driversLicenseNumberTextView: EditText

    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var vehicleInteractor: IVehicleInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        forwardOnWelcomeScreen()
        initViewModel()
        initView()
        registerObservers()
    }

    /** Инициализация View */
    private fun initView() {
        vehicleNumberTextView = findViewById(R.id.vehicle_number_text_view)
        vehicleRegistrationCertificateTextView = findViewById(R.id.vehicle_registration_certificate_text_view)
        driversLicenseNumberTextView = findViewById(R.id.drivers_license_number_text_view)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.onDisplayVehicleData()
    }

    /** Инициализация ViewModel */
    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this,
            ViewModelProviderFactory(
                MainViewModel(
                    vehicleInteractor
                )
            )
        ).get(MainViewModel::class.java)
    }

    /** Перенаправить на приветсвенный экрна */
    private fun forwardOnWelcomeScreen() {
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        if (!sharedPreferences.getBoolean(IS_VISIBLE_WIZARD_SCREEN_KEY, false)) {
            startActivity(WizardActivity.newIntent(this, WizardStateScreen.VEHICLE_NUMBER))
            finish()
        } else if (!sharedPreferences.getBoolean(IS_VISIBLE_WALK_THROUGH_SCREEN_ACTIVITY_KEY, false)) {
            startActivity(WalkThroughActivity.newIntent(this))
            finish()
        }
    }

    /** Отобразить данные по ТС, СТС и ВУ на экране */
    private fun displayVehicleData(vehicleModel: VehicleModel) {
        val (vehicleNumber, vehicleRegistrationCertificate, driversLicenseNumber) = vehicleModel

        if (vehicleNumber.isNotEmpty()) vehicleNumberTextView.setText(vehicleNumber)
        if (vehicleRegistrationCertificate.isNotEmpty()) vehicleRegistrationCertificateTextView.setText(vehicleRegistrationCertificate)
        if (driversLicenseNumber.isNotEmpty()) driversLicenseNumberTextView.setText(driversLicenseNumber)
    }

    /** регистрация подписчиков */
    private fun registerObservers() {
        mainViewModel.getVehicleLiveDate().observe(this, this::displayVehicleData)
    }

    companion object {
        const val TAG = "MainActivity"

        /** создание интента активити с необходимыми параметрами */
        fun newIntent(context: Context) : Intent {
            return Intent(context, MainActivity::class.java)
        }

    }

}
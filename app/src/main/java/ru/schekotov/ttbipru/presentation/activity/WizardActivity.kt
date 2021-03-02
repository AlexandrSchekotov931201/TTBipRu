package ru.schekotov.ttbipru.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import ru.schekotov.ttbipru.App
import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.constans.SharedPreferencesConst
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.IS_VISIBLE_WIZARD_SCREEN_KEY
import ru.schekotov.ttbipru.data.model.VehicleModel
import ru.schekotov.ttbipru.domain.IVehicleInteractor
import ru.schekotov.ttbipru.enums.WizardStateScreen
import ru.schekotov.ttbipru.presentation.ViewModelProviderFactory
import ru.schekotov.ttbipru.presentation.viewModel.WizardViewModel
import javax.inject.Inject

class WizardActivity : AppCompatActivity() {

    private lateinit var toolBar: Toolbar
    private lateinit var titleToolbar: TextView
    private lateinit var wizardNextButton: Button
    private lateinit var wizardSkipButton: Button

    private lateinit var currentWizardStateScreen: WizardStateScreen
    private lateinit var nextWizardStateScreen: WizardStateScreen

    private lateinit var wizardViewModel: WizardViewModel

    private lateinit var editText: EditText

    private lateinit var vehicleMap: HashMap<WizardStateScreen, String>

    @Inject
    lateinit var vehicleInteractor: IVehicleInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wizard_activity)
        initViewModel()
        initView()
        initListener()
    }

    override fun onStart() {
        super.onStart()
        currentWizardStateScreen = intent.getSerializableExtra(WIZARD_STATE) as WizardStateScreen
        nextWizardStateScreen = getNextWizardStateScreen()
        vehicleMap = intent.getSerializableExtra(WIZARD_VEHICLE_MAP) as HashMap<WizardStateScreen, String>
        titleToolbar.text = getString(currentWizardStateScreen.state.title)
    }

    /** Инициализация ViewModel */
    private fun initViewModel() {
        wizardViewModel = ViewModelProvider(
            viewModelStore,
            ViewModelProviderFactory(
                WizardViewModel(vehicleInteractor)
            )
        ).get(WizardViewModel::class.java)
    }

    /** Инициализация View */
    private fun initView() {
        toolBar = findViewById(R.id.wizard_toolbar)
        titleToolbar = findViewById(R.id.wizard_toolbar_title)
        editText = findViewById(R.id.edit_text_text_person_name)
        wizardNextButton = findViewById(R.id.wizard_next_button)
        wizardSkipButton = findViewById(R.id.wizard_skip_button)
    }

    /** Инициализация Listener */
    //TODO Оптимизировать код в слушателях кнопок (Избавиться от дублирования кода)
    private fun initListener() {
        wizardNextButton.setOnClickListener {
            vehicleMap[currentWizardStateScreen] = editText.text.toString()
            if (currentWizardStateScreen == WizardStateScreen.DRIVERS_LICENSE_NUMBER) {
                getSharedPreferences(SharedPreferencesConst.APP_PREFERENCES, Context.MODE_PRIVATE).edit()
                    .putBoolean(IS_VISIBLE_WIZARD_SCREEN_KEY, true)
                    .apply()
                wizardViewModel.insertData(getVehicleModel())
                startActivity(WalkThroughActivity.newIntent(this))
                ActivityCompat.finishAffinity(this)
            } else {
                startActivity(newIntent(this, nextWizardStateScreen, vehicleMap))
            }
        }
        wizardSkipButton.setOnClickListener {
            showAlertWithTwoButton()
        }
    }

    //TODO Вынести в отдельный класс работы с алертами
    private fun showAlertWithTwoButton() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setMessage("Вы уверенны что хотите пропустить этот шаг?")
        alertDialog.setPositiveButton("ДА") { _, _ ->
            if (currentWizardStateScreen == WizardStateScreen.DRIVERS_LICENSE_NUMBER) {
                getSharedPreferences(SharedPreferencesConst.APP_PREFERENCES, Context.MODE_PRIVATE).edit()
                    .putBoolean(IS_VISIBLE_WIZARD_SCREEN_KEY, true)
                    .apply()
                startActivity(WalkThroughActivity.newIntent(this))
                ActivityCompat.finishAffinity(this)
            } else {
                startActivity(newIntent(this, nextWizardStateScreen))
            }
        }
        alertDialog.setNegativeButton("НЕТ") { dialog, _ -> dialog.cancel() }
        alertDialog.show()
    }

    private fun getNextWizardStateScreen(): WizardStateScreen {
        val wizardStateScreens = WizardStateScreen.values()
        for (index in wizardStateScreens.indices) {
            if (currentWizardStateScreen == wizardStateScreens[index] && index < wizardStateScreens.size - 1) {
                return wizardStateScreens[index + 1]
            }
        }
        return WizardStateScreen.VEHICLE_NUMBER
    }

    private fun getVehicleModel() : VehicleModel{
        val vehicleNumber = vehicleMap[WizardStateScreen.VEHICLE_NUMBER]
        val vehicleRegistrationCertificate = vehicleMap[WizardStateScreen.REGISTRATION_CERTIFICATE_NUMBER]
        val driversLicenseNumber = vehicleMap[WizardStateScreen.DRIVERS_LICENSE_NUMBER]
        if (vehicleNumber != null
            && vehicleRegistrationCertificate != null
            && driversLicenseNumber != null) {
            return VehicleModel(vehicleNumber, vehicleRegistrationCertificate, driversLicenseNumber)
        }
        return VehicleModel()
    }

    companion object {
        private const val TAG = "WizardActivity"
        private const val WIZARD_STATE = "WIZARD_STATE"
        private const val WIZARD_VEHICLE_MAP = "WIZARD_VEHICLE_MAP"

        fun newIntent(
            context: Context,
            wizardStateScreen: WizardStateScreen,
            vehicleMap: HashMap<WizardStateScreen, String> = hashMapOf()
        ): Intent {
            return Intent(context, WizardActivity::class.java).apply {
                putExtra(WIZARD_STATE, wizardStateScreen)
                putExtra(WIZARD_VEHICLE_MAP, vehicleMap)
            }
        }

    }
}
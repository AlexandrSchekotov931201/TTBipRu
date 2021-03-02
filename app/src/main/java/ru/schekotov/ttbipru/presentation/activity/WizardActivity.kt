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
import androidx.lifecycle.observe
import ru.schekotov.ttbipru.App
import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.constans.SharedPreferencesConst
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.IS_VISIBLE_WIZARD_SCREEN_KEY
import ru.schekotov.ttbipru.domain.IVehicleInteractor
import ru.schekotov.ttbipru.enums.WizardStateScreen
import ru.schekotov.ttbipru.presentation.ViewModelProviderFactory
import ru.schekotov.ttbipru.presentation.viewModel.WizardViewModel
import javax.inject.Inject

/**
 * Активити визард экранов
 *
 * @author Щёкотов Александр
 */
class WizardActivity : AppCompatActivity() {

    private lateinit var toolBar: Toolbar
    private lateinit var titleToolbar: TextView
    private lateinit var wizardNextButton: Button
    private lateinit var wizardSkipButton: Button

    private lateinit var wizardViewModel: WizardViewModel

    private lateinit var editText: EditText

    @Inject
    lateinit var vehicleInteractor: IVehicleInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wizard_activity)
        initViewModel()
        initView()
        initListener()
        registerObservers()
    }

    override fun onStart() {
        super.onStart()
        titleToolbar.text = getString(wizardViewModel.getCurrentWizardStateScreen().state.title)
    }

    /** Инициализация ViewModel */
    private fun initViewModel() {
        wizardViewModel = ViewModelProvider(this,
            ViewModelProviderFactory(
                WizardViewModel(
                    vehicleInteractor,
                    intent.getSerializableExtra(WIZARD_STATE) as WizardStateScreen,
                    intent.getSerializableExtra(WIZARD_VEHICLE_MAP) as HashMap<WizardStateScreen, String>
                )
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
    private fun initListener() {
        wizardNextButton.setOnClickListener {
            wizardViewModel.onWizardNext()
        }
        wizardSkipButton.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setMessage(getString(R.string.skip_alert_text))
                setPositiveButton(getString(R.string.skip_alert_positive_button_title)) { _, _ ->
                    wizardViewModel.onWizardNext()
                }
                setNegativeButton(getString(R.string.skip_alert_negative_button_title)) { dialog, _ -> dialog.cancel() }
                show()
            }
        }
    }

    /**
     * Действие по переходу на следующий экран визарда
     *
     * @param currentWizardStateScreen текущее состояние отображения экрана визарда
     */
    private fun wizardNext(currentWizardStateScreen: WizardStateScreen) {
        wizardViewModel.getVehicleMap()[currentWizardStateScreen] = editText.text.toString()
        if (currentWizardStateScreen == WizardStateScreen.DRIVERS_LICENSE_NUMBER) {
            getSharedPreferences(SharedPreferencesConst.APP_PREFERENCES, Context.MODE_PRIVATE).edit()
                .putBoolean(IS_VISIBLE_WIZARD_SCREEN_KEY, true)
                .apply()
            wizardViewModel.insertData(wizardViewModel.getVehicleModel())
            startActivity(WalkThroughActivity.newIntent(this))
            ActivityCompat.finishAffinity(this)
        } else {
            startActivity(newIntent(this, wizardViewModel.getNextWizardStateScreen(), wizardViewModel.getVehicleMap()))
        }
    }

    /** регистрация подписчиков */
    private fun registerObservers() {
        wizardViewModel.getWizardStateScreenLiveDate().observe(this, this::wizardNext)
    }

    companion object {
        private const val TAG = "WizardActivity"
        private const val WIZARD_STATE = "WIZARD_STATE"
        private const val WIZARD_VEHICLE_MAP = "WIZARD_VEHICLE_MAP"

        /** создание интента активити с необходимыми параметрами */
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
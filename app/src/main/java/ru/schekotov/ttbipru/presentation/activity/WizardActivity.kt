package ru.schekotov.ttbipru.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
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
import ru.schekotov.ttbipru.presentation.enums.WizardStateScreen
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
    private lateinit var imageView: ImageView
    private lateinit var titleToolbar: TextView
    private lateinit var errorTextFilling: TextView
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
        imageView.setImageResource(wizardViewModel.getCurrentWizardStateScreen().state.img)
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
        toolBar = findViewById(R.id.toolbar_wizard)
        imageView = findViewById(R.id.image_view_wizard)
        titleToolbar = findViewById(R.id.text_view_wizard_toolbar_title)
        errorTextFilling = findViewById(R.id.text_view_error_filling_edit_text)
        editText = findViewById(R.id.edit_text_text_person_name)
        wizardNextButton = findViewById(R.id.button_wizard_next)
        wizardSkipButton = findViewById(R.id.button_wizard_skip)
    }

    /** Инициализация Listener */
    private fun initListener() {
        wizardNextButton.setOnClickListener {
            if (editText.text.toString().isNotEmpty()) {
                wizardViewModel.onWizardNext()
            } else {
                wizardViewModel.onErrorField()
            }
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
        editText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                //Нет необходимости в реализации
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Нет необходимости в реализации
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                wizardViewModel.onGoodField()
            }
        })
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

    /** Обработка ошибки заполения поля ввода визарда */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun errorFillingInField(isError: Boolean) {
        if (isError) {
            editText.backgroundTintList = getColorStateList(R.color.strong_orange)
            errorTextFilling.visibility = View.VISIBLE
        } else {
            editText.backgroundTintList = getColorStateList(R.color.green)
            errorTextFilling.visibility = View.GONE
        }
    }

    /** регистрация подписчиков */
    private fun registerObservers() {
        wizardViewModel.getWizardStateScreenLiveDate().observe(this, this::wizardNext)
        wizardViewModel.getErrorFieldLiveDateLiveDate().observe(this, this::errorFillingInField)
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
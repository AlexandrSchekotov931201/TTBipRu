package ru.schekotov.ttbipru.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import ru.schekotov.ttbipru.App
import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.constans.SharedPreferencesConst
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.IS_FILLED_IN_WELCOME_SCREEN_DATA_KEY
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.IS_VISIBLE_WELCOME_SCREEN_ACTIVITY_KEY
import ru.schekotov.ttbipru.data.DBHelper
import ru.schekotov.ttbipru.enums.WizardStateScreen
import javax.inject.Inject

class WizardActivity : AppCompatActivity() {

    private lateinit var toolBar: Toolbar
    private lateinit var titleToolbar: TextView
    private lateinit var wizardNextButton: Button
    private lateinit var wizardSkipButton: Button

    private lateinit var currentWizardStateScreen: WizardStateScreen
    private lateinit var nextWizardStateScreen: WizardStateScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wizard_activity)
        initView()
        initListener()
    }

    override fun onStart() {
        super.onStart()
        currentWizardStateScreen = intent.getSerializableExtra(WIZARD_STATE) as WizardStateScreen
        nextWizardStateScreen = getNextWizardStateScreen()
        titleToolbar.text = getString(currentWizardStateScreen.state.title)
    }

    /** Инициализация View */
    private fun initView() {
        toolBar = findViewById(R.id.wizard_toolbar)
        titleToolbar = findViewById(R.id.wizard_toolbar_title)
        wizardNextButton = findViewById(R.id.wizard_next_button)
        wizardSkipButton = findViewById(R.id.wizard_skip_button)
    }

    /** Инициализация Listener */
    //TODO Оптимизировать код в слушателях кнопок (Избавиться от дублирования кода)
    private fun initListener() {
        wizardNextButton.setOnClickListener {
            if (currentWizardStateScreen == WizardStateScreen.DRIVERS_LICENSE_NUMBER) {
                getSharedPreferences(SharedPreferencesConst.APP_PREFERENCES, Context.MODE_PRIVATE).edit()
                    .putBoolean(IS_VISIBLE_WELCOME_SCREEN_ACTIVITY_KEY, true)
                    .putBoolean(IS_FILLED_IN_WELCOME_SCREEN_DATA_KEY, true)
                    .apply()
                startActivity(WalkThroughActivity.newIntent(this))
                ActivityCompat.finishAffinity(this)
            } else {
                startActivity(newIntent(this, nextWizardStateScreen))
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
                    .putBoolean(IS_VISIBLE_WELCOME_SCREEN_ACTIVITY_KEY, true)
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

    companion object {
        const val TAG = "WizardActivity"
        const val WIZARD_STATE = "WizardActivity"

        fun newIntent(context: Context, wizardStateScreen: WizardStateScreen): Intent {
            val intent = Intent(context, WizardActivity::class.java)
            intent.putExtra(WIZARD_STATE, wizardStateScreen)
            return intent
        }
    }
}
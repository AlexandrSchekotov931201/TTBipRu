package ru.schekotov.ttbipru.presentation.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.schekotov.ttbipru.App
import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.APP_PREFERENCES
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.IS_VISIBLE_WALK_THROUGH_SCREEN_ACTIVITY_KEY
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.IS_VISIBLE_WIZARD_SCREEN_KEY
import ru.schekotov.ttbipru.domain.IVehicleInteractor
import ru.schekotov.ttbipru.enums.WizardStateScreen
import javax.inject.Inject

/**
 * Активити главного экрана
 *
 * @author Щёкотов Александр
 */
class MainActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    private lateinit var vehicleNumberTextView: TextView
    private lateinit var vehicleRegistrationCertificateTextView: TextView
    private lateinit var driversLicenseNumberTextView: TextView

    @Inject
    lateinit var vehicleInteractor: IVehicleInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        forwardOnWelcomeScreen()
        initView()
    }

    /** Инициализация View */
    private fun initView() {
        vehicleNumberTextView = findViewById(R.id.vehicle_number_text_view)
        vehicleRegistrationCertificateTextView = findViewById(R.id.vehicle_registration_certificate_text_view)
        driversLicenseNumberTextView = findViewById(R.id.drivers_license_number_text_view)

        val vehicle = vehicleInteractor.getVehicle()
        vehicleNumberTextView.text = vehicle.vehicleNumber
        vehicleRegistrationCertificateTextView.text = vehicle.vehicleRegistrationCertificate
        driversLicenseNumberTextView.text = vehicle.driversLicenseNumber
    }

    /**
     * Перенаправить на приветсвенный экрна
     */
    private fun forwardOnWelcomeScreen() {
        if (!sharedPreferences!!.getBoolean(IS_VISIBLE_WIZARD_SCREEN_KEY, false)) {
            startActivity(
                WizardActivity.newIntent(
                    this,
                    WizardStateScreen.VEHICLE_NUMBER
                )
            )
            finish()
        } else if (!sharedPreferences!!.getBoolean(IS_VISIBLE_WALK_THROUGH_SCREEN_ACTIVITY_KEY, false)) {
            startActivity(
                WalkThroughActivity.newIntent(
                    this
                )
            )
            finish()
        }
    }

    companion object {
        const val TAG = "MainActivity"

        /** создание интента активити с необходимыми параметрами */
        fun newIntent(context: Context) : Intent {
            return Intent(context, MainActivity::class.java)
        }

    }

}
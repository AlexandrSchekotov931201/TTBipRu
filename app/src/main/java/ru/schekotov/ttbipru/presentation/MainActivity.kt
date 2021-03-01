package ru.schekotov.ttbipru.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.schekotov.ttbipru.App
import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.APP_PREFERENCES
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.IS_VISIBLE_WALK_THROUGH_SCREEN_ACTIVITY_KEY
import ru.schekotov.ttbipru.constans.SharedPreferencesConst.IS_VISIBLE_WELCOME_SCREEN_ACTIVITY_KEY
import ru.schekotov.ttbipru.enums.WizardStateScreen

class MainActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        forwardOnWelcomeScreen()
    }

    /**
     * Перенаправить на приветсвенный экрна
     */
    private fun forwardOnWelcomeScreen() {
        if (!sharedPreferences!!.getBoolean(IS_VISIBLE_WELCOME_SCREEN_ACTIVITY_KEY, false)) {
            startActivity(WizardActivity.newIntent(this, WizardStateScreen.VEHICLE_NUMBER))
            finish()
        } else if (!sharedPreferences!!.getBoolean(IS_VISIBLE_WALK_THROUGH_SCREEN_ACTIVITY_KEY, false)) {
            startActivity(WalkThroughActivity.newIntent(this))
            finish()
        }
    }

    companion object {
        const val TAG = "MainActivity"

        fun newIntent(context: Context) : Intent {
            return Intent(context, MainActivity::class.java)
        }

    }

}
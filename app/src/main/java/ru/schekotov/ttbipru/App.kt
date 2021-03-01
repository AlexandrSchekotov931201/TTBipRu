package ru.schekotov.ttbipru

import android.app.Application
import ru.schekotov.ttbipru.di.AppComponent
import ru.schekotov.ttbipru.di.AppModule
import ru.schekotov.ttbipru.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        private lateinit var appComponent: AppComponent

        fun getAppComponent(): AppComponent {
            return appComponent
        }
    }

}
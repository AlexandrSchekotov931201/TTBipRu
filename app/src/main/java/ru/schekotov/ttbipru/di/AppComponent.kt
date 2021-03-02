package ru.schekotov.ttbipru.di

import dagger.Component
import ru.schekotov.ttbipru.presentation.activity.MainActivity
import ru.schekotov.ttbipru.presentation.activity.WizardActivity
import javax.inject.Singleton

@Component(modules = [AppModule::class, FeatureModule::class])
@Singleton
interface AppComponent {

    //Activity
    fun inject(wizardActivity: WizardActivity)
    fun inject(mainActivity: MainActivity)

}
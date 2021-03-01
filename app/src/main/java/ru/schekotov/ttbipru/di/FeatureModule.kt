package ru.schekotov.ttbipru.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.schekotov.ttbipru.data.DBHelper
import javax.inject.Singleton

@Module (includes = [AppModule::class])
class FeatureModule {

    @Provides
    @Singleton
    fun provideDBHelper(context: Context): DBHelper {
        return DBHelper(context)
    }

}
package ru.schekotov.ttbipru.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(context: Context) {

    private val context: Context = context.applicationContext

    @Provides
    @Singleton
    internal fun provideApplicationContext() = context

}
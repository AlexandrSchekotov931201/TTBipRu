package ru.schekotov.ttbipru.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WizardContentModel(
    @StringRes val title: Int,
    @DrawableRes val img: Int
)
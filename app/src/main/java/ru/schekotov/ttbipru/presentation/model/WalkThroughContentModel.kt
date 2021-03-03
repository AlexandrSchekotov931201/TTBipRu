package ru.schekotov.ttbipru.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WalkThroughContentModel(
    @StringRes val headerText: Int,
    @DrawableRes val img: Int
)
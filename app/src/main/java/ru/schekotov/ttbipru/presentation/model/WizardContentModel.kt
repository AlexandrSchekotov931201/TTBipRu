package ru.schekotov.ttbipru.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Модель данных которая хранит в себе информацию о контекте визард экрана
 *
 * @author Щёкотов Александр
 */
data class WizardContentModel(
    @StringRes val title: Int,
    @DrawableRes val img: Int
)
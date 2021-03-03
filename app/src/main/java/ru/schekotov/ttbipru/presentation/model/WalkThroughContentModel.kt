package ru.schekotov.ttbipru.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


/**
 * Модель данных которая хранит в себе информацию о контекте WalkThrough экрана
 *
 * @author Щёкотов Александр
 */
data class WalkThroughContentModel(
    @StringRes val headerText: Int,
    @DrawableRes val img: Int
)
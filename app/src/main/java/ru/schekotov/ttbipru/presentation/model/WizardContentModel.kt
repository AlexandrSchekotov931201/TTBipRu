package ru.schekotov.ttbipru.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Модель данных которая хранит в себе информацию о контенте визард экрана
 *
 * @property title текст заголовка в toolbar на экране визарда
 * @property img картинка над заголовокм в toolbar на экране визарда
 * @author Щёкотов Александр
 */
data class WizardContentModel(
    @StringRes val title: Int,
    @DrawableRes val img: Int
)
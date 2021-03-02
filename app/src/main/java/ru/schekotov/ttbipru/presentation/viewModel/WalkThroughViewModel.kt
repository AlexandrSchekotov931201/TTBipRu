package ru.schekotov.ttbipru.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel для работы с WalkThrough экранами
 *
 * @property defaultPositionDots дефолтное значение для позиции индикатора (Точки)
 * @author Щёкотов Александр
 */
class WalkThroughViewModel(
    defaultPositionDots: Int = 0
) : ViewModel() {

    private val showNextButtonLiveDate: MutableLiveData<Boolean> = MutableLiveData()
    private val showDotsIndicatorLiveDate: MutableLiveData<Int> = MutableLiveData()

    init {
        showDotsIndicatorLiveDate.value = defaultPositionDots
    }

    /** Произошло событие отображения индикатора с точками */
    fun onShowDotsIndicator(position: Int) {
        showDotsIndicatorLiveDate.value = position
    }

    /** Произошло событие отображения кнопки для продолжения */
    fun onShowNextButton() {
        showNextButtonLiveDate.value = true
    }

    /** Произошло событие сокрытия кнопки для продолжения */
    fun onHideNextButton() {
        showNextButtonLiveDate.value = false
    }

    /** Получить LiveData отображения кнопки для продолжения */
    fun getShowNextButtonLiveDate() : LiveData<Boolean> {
        return showNextButtonLiveDate
    }

    /** Получить LiveData отображения индикатора с точками */
    fun getShowDotsIndicatorLiveDate() : LiveData<Int> {
        return showDotsIndicatorLiveDate
    }

}
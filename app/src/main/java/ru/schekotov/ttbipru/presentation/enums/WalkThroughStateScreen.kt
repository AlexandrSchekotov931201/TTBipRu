package ru.schekotov.ttbipru.presentation.enums

import ru.schekotov.ttbipru.R
import ru.schekotov.ttbipru.presentation.model.WalkThroughContentModel

/**
 * Перечесление состояний экранов WalkThrough экранов
 *
 * @author Щёкотова Александр
 */
enum class WalkThroughStateScreen(val state: WalkThroughContentModel) {

    /** экран с текстом "Оплачивайте штрафы со скидкой 50%" */
    PAY_PENALTIES(
        WalkThroughContentModel(
            R.string.pay_penalties_with_50_discount,
            R.drawable.ic_round_payment_150
        )
    ),

    /** экран с текстом "Получайте уведомления о новых штрафах" */
    GET_NOTIFIED(
        WalkThroughContentModel(
            R.string.get_notified_of_new_fines,
            R.drawable.ic_baseline_notifications_active_150
        )
    ),

    /** экран с текстом "Гарантия погашения, квитанция как в банке" */
    REPAYMENT_GUARANTEE(
        WalkThroughContentModel(
            R.string.repayment_guarantee_receipt_as_in_the_bank,
            R.drawable.ic_outline_check_circle_outline_150
        )
    ),

    /** экран с текстом "Соблюдайте правила дорожного движения" */
    OBEY_TRAFFIC(
        WalkThroughContentModel(
            R.string.obey_traffic_rules,
            R.drawable.ic_baseline_bus_alert_150
        )
    )
}
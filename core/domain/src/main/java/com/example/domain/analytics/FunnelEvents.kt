package com.example.domain.analytics

// 1. Старт онбординга
object OnboardingStartedEvent : IAnalyticsEvent {
    override val eventName = "funnel_onboarding_started"
}

// 2. Успешное завершение онбординга
object OnboardingCompletedEvent : IAnalyticsEvent {
    override val eventName = "funnel_onboarding_completed"
}

// 3. Создание первого счета
class FirstStorageCreatedEvent(val currency: String) : IAnalyticsEvent {
    override val eventName = "funnel_first_storage_created"
    override val params = mapOf(
        "currency" to currency
    )
}
// 4. Первая транзакция (пополнение/списание)
class FirstTransactionEvent(val amount: Double, val type: String) : IAnalyticsEvent {
    override val eventName = "funnel_first_transaction"
    override val params = mapOf(
        "amount" to amount,
        "type" to type // "income" или "expense"
    )
}

class OpenAppEvent(
    val isFirstLaunch: Boolean,
    val authStatus: Boolean
) : IAnalyticsEvent {
    override val eventName = "funnel_open_app"
    override val params = mapOf(
        "is_first_launch" to isFirstLaunch,
        "is_authorized" to authStatus
    )
}

object StorageCreateEvent: IAnalyticsEvent{
    override val eventName = "funnel_storage_create"
}

object OperationCreateEvent: IAnalyticsEvent{
    override val eventName = "funnel_operation_create"
}

object OpenBleScanner: IAnalyticsEvent{
    override val eventName = "funnel_open_ble_scanner"
}

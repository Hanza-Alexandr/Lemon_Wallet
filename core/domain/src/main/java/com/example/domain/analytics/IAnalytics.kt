package com.example.domain.analytics

interface IAnalytics {
    /**
     * Отправка кастомного события аналитики
     */
    fun trackEvent(event: IAnalyticsEvent)

    /**
     * Логирование обычных текстовых сообщений (Crashlytics Logs)
     */
    fun log(message: String)

    /**
     * Сохранение ключей состояния (Firebase Crashlytics Custom Keys)
     */
    fun setCustomKey(key: String, value: Any)

    /**
     * Установка ID пользователя
     */
    fun setUserId(id: String)

    /**
     * Запись некритичных (handled) ошибок
     */
    fun recordException(throwable: Throwable)
}

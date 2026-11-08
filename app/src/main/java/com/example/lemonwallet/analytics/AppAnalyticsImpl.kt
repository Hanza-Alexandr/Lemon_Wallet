package com.example.lemonwallet.analytics

import android.os.Bundle
import com.example.domain.analytics.IAnalytics
import com.example.domain.analytics.IAnalyticsEvent
import so.hawk.Hawk
import javax.inject.Inject

class AppAnalyticsImpl @Inject constructor(
) : IAnalytics {

    override fun trackEvent(event: IAnalyticsEvent) {
        // Hawk - это трекер ошибок, он не собирает продуктовую аналитику
        // Сюда в будущем можно будет добавить AppMetrica или Firebase Analytics
    }

    override fun log(message: String) {
        // Логирование простых сообщений
        Hawk.catch(Exception(message))
    }

    override fun setCustomKey(key: String, value: Any) {
        // В базовой версии Hawk нет прямых кастомных тегов, как в Crashlytics,
        // но можно расширять контекст. Оставляем пустым или реализовываем через контекст.
    }

    override fun setUserId(id: String) {
        // Привязка пользователя к ошибке
        Hawk.setUser(id)
    }

    override fun recordException(throwable: Throwable) {
        // Отправка перехваченной ошибки в Hawk.so
        Hawk.catch(throwable)
    }
}

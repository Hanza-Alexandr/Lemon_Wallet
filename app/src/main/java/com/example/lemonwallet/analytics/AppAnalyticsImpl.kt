package com.example.lemonwallet.analytics

import android.os.Bundle
import com.example.domain.analytics.IAnalytics
import com.example.domain.analytics.IAnalyticsEvent
import android.content.Context
import com.example.lemonwallet.LemonWalletApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppAnalyticsImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : IAnalytics {

    override fun trackEvent(event: IAnalyticsEvent) {
        // Сюда в будущем можно будет добавить AppMetrica или Firebase Analytics
    }

    override fun log(message: String) {
        // Логирование простых сообщений
    }

    override fun setCustomKey(key: String, value: Any) {
        // Оставляем пустым или реализовываем через контекст.
    }

    override fun setUserId(id: String) {
        // Привязка пользователя
    }

    override fun recordException(throwable: Throwable) {
        // Обработка перехваченной ошибки
        val app = context.applicationContext as LemonWalletApp
        app.exceptionCatcher.caught(throwable)
    }
}

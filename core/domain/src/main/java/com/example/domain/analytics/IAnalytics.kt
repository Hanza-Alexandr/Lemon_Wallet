package com.example.domain.analytics

interface IAnalytics {
    fun trackEvent(event: IAnalyticsEvent)
    fun log(message: String)
    fun setCustomKey(key: String, value: Any)
    fun setUserId(id: String)
    fun recordException(throwable: Throwable)
}

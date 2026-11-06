package com.example.domain.analytics

interface IAnalyticsEvent {
    val eventName: String
    val params: Map<String, Any>
        get() = emptyMap()
}

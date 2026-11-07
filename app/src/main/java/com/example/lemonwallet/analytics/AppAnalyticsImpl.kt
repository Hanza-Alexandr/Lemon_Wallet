package com.example.lemonwallet.analytics

import android.os.Bundle
import android.util.Log
import com.example.domain.analytics.IAnalytics
import com.example.domain.analytics.IAnalyticsEvent
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.appmetrica.analytics.AppMetrica
import javax.inject.Inject

class AppAnalyticsImpl @Inject constructor(
    private val analytics: FirebaseAnalytics,
    private val crashlytics: FirebaseCrashlytics
) : IAnalytics {

    override fun trackEvent(event: IAnalyticsEvent) {
        val bundle = Bundle().apply {
            event.params.forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Double -> putDouble(key, value)
                    is Float -> putFloat(key, value)
                    is Boolean -> putBoolean(key, value)
                    else -> putString(key, value.toString())
                }
            }
        }
        analytics.logEvent(event.eventName, bundle)

        if(event.params.isEmpty()){
            AppMetrica.reportEvent(event.eventName)
        }
        else{
            AppMetrica.reportEvent(event.eventName, event.params.toMap())
        }
        Log.i("ANALYTICS",event.eventName )
    }

    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun setCustomKey(key: String, value: Any) {
        when (value) {
            is String -> crashlytics.setCustomKey(key, value)
            is Int -> crashlytics.setCustomKey(key, value)
            is Long -> crashlytics.setCustomKey(key, value)
            is Double -> crashlytics.setCustomKey(key, value)
            is Float -> crashlytics.setCustomKey(key, value)
            is Boolean -> crashlytics.setCustomKey(key, value)
            else -> crashlytics.setCustomKey(key, value.toString())
        }
    }

    override fun setUserId(id: String) {
        analytics.setUserId(id)
        crashlytics.setUserId(id)
    }

    override fun recordException(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }
}

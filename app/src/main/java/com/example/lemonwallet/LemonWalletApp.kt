package com.example.lemonwallet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig

@HiltAndroidApp
class LemonWalletApp : Application(){
    override fun onCreate() {
        super.onCreate()
        val config = AppMetricaConfig
            .newConfigBuilder("01d5c1c5-2389-4473-8603-d2818b8f4fb2")
            .build()
        AppMetrica.activate(this, config)

    }
}
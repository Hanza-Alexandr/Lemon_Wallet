package com.example.lemonwallet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import so.hawk.Hawk

@HiltAndroidApp
class LemonWalletApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Инициализация Hawk.so для отлова крашей
        // ЗАМЕНИТЕ НА ВАШ ТОКЕН ИЗ ПРОЕКТА HAWK.SO
        Hawk.init(this, "ВАШ_HAWK_INTEGRATION_TOKEN")
    }
}

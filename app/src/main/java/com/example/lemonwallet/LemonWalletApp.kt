package com.example.lemonwallet

import android.app.Application
import com.hawkcatcher.android.HawkExceptionCatcher
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class LemonWalletApp : Application() {
    lateinit var exceptionCatcher: HawkExceptionCatcher
    override fun onCreate() {
        super.onCreate()
        defineExceptionCatcher()
    }
    private fun defineExceptionCatcher() {
        // Передаем контекст (this) и вызываем start()
        exceptionCatcher = HawkExceptionCatcher(this)
        exceptionCatcher.start()
    }
}

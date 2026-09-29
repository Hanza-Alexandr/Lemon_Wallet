package com.example.main_screen.model

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.domain.IServiceController
import com.example.service.TestForegroundService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ForegroundTestServiceController @Inject constructor( @ApplicationContext private val context: Context): IServiceController {
    override fun startService() {
        val intent = Intent(context, TestForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
        Log.d("🗿", "ЗАУПУСК СЕРВИСА")
    }

    override fun stopService() {
        Log.d("🗿", "СТОП СЕРВИСА")

        val intent = Intent(context, TestForegroundService::class.java)
        context.stopService(intent)
    }
}
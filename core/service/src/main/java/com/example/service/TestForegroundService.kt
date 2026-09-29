package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.domain.ForegroundTestServiceData
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class TestForegroundService: Service() {
    @Inject
    lateinit var serviceDataRepo: ForegroundTestServiceData
    private val serviceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val binder = LocalBinder()
    override fun onBind(p0: Intent?): IBinder {
        Log.d("🗿", "Сервис привязан (Bound)")
        return binder
    }

    inner class LocalBinder: Binder(){
        fun getService() : TestForegroundService = this@TestForegroundService
    }
    companion object{
        private const val CHANEL_ID = "test_chanel_id"
        private const val NOTIFICATION_ID = 1
        const val ACTION_STOP = "ACTION_STOP_SERVICE"
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent?.action == ACTION_STOP){
            stopService()
            return START_NOT_STICKY
        }
        val notification = createNoty("",this)
        startForeground(
            NOTIFICATION_ID,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
        )
        startLogic(this)
        return START_STICKY

    }

    private fun startLogic(context: Context){
        serviceScope.launch {
            while (true){
                Log.d("🗿", "Оно работает")
                val num = Random.nextInt(100,500)
                serviceDataRepo.emit("$num")
                createNoty(num.toString(), context)
                delay(1000)
            }
        }
    }
    private fun stopService(){
        serviceScope.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNoty(value: String,context: Context): Notification {
        val stopIntent = Intent(context,TestForegroundService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            context,
            0,
            stopIntent,
            PendingIntent.FLAG_IMMUTABLE // Важно для Android 12+
        )
        return NotificationCompat.Builder(context, CHANEL_ID)
            .setContentTitle("Test service")
            .setContentText("@${value}")
            .setSmallIcon(android.R.drawable.ic_menu_add)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .addAction(android.R.drawable.ic_menu_add, "Stop", stopPendingIntent)
            .build()

    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    private fun createChanel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANEL_ID,
                "Test chenale",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createChanel()
    }
}
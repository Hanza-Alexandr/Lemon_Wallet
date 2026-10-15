package com.example.ble_screen.useCase

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.example.ble_screen.model.BleDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/**
 * Аккуратнее с тем где вызывается BleScanUseCase. Он будет использовать диспатчеар на котором вызван
 */

class BleScanService @Inject constructor(private val bleScanUseCase: BleScanUseCase){

}


class BleScanUseCase @Inject constructor(private val bleAdapter: BluetoothAdapter?){
    private val bleScanner = bleAdapter?.bluetoothLeScanner

    @SuppressLint("MissingPermission")
    operator fun invoke(delayEmit: Long = 5000L, deviceTimeout: Long = 5000L ): Flow<List<BleDevice>> = callbackFlow {

        // Накапливаем устройства по MAC-адресу
        val devicesMap = mutableMapOf<String, BleDevice>()

        val scanCallback = object : ScanCallback() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                val bleDevice = result.toBleDevice(System.currentTimeMillis())
                devicesMap[result.device.address] = bleDevice
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onBatchScanResults(results: List<ScanResult>) {
                results.forEach {
                    val bleDevice = it.toBleDevice(System.currentTimeMillis())
                    devicesMap[it.device.address] = bleDevice
                }
            }

            override fun onScanFailed(errorCode: Int) {
                close(RuntimeException("Сканирование BLE завершилось с ошибкой: $errorCode"))
            }
        }


        val settings = ScanSettings.Builder()
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
            .build()

        bleScanner?.startScan(null, settings, scanCallback)

        /**
         * Корутина периодической отправки списка устройств с настраиваемой задержкой.
         * 1. Задержка между отправкой нового списка
         * 2. удаление устрйоств если время их последнего обнаружения было больше deviceTimeout
         */
        val emitJob = launch {
            while (isActive){
                //1.
                delay(delayEmit.milliseconds)
                val now = System.currentTimeMillis()
                //2.
                devicesMap.values.removeIf { timedResult ->
                    now - timedResult.lastSeenTimestamp > deviceTimeout
                }
                trySend(devicesMap.values.sortedBy { it.rssi })
            }
        }

        awaitClose {
            emitJob.cancel()
            bleScanner?.stopScan(scanCallback)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun ScanResult.toBleDevice(lastSeenTimestamp: Long): BleDevice{
    return BleDevice(
        name = this.scanRecord?.deviceName,
        rssi = this.rssi.toDouble(),
        address = this.device.address,
        txPower = this.txPower.toDouble(),
        lastSeenTimestamp = lastSeenTimestamp
    )
}


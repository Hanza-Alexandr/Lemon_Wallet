package com.example.ble_screen.useCase

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.example.ble_screen.model.BleDevice
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class BleScanUseCase @Inject constructor(private val bleAdapter: BluetoothAdapter?){
    private val bleScanner = bleAdapter?.bluetoothLeScanner


    @SuppressLint("MissingPermission")
    operator fun invoke(): Flow<List<BleDevice>> = callbackFlow {

        // Накапливаем устройства по MAC-адресу
        val devicesMap = LinkedHashMap<String, BleDevice>()

        val scanCallback = object : ScanCallback() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                devicesMap[result.device.address] = result.toBleDevice(System.currentTimeMillis())
                trySend(devicesMap.values.toList())
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onBatchScanResults(results: List<ScanResult>) {
                for (result in results) {
                    devicesMap[result.device.address] = result.toBleDevice(System.currentTimeMillis())
                }
                trySend(devicesMap.values.toList())
            }

            override fun onScanFailed(errorCode: Int) {
                close(RuntimeException("Сканирование BLE завершилось с ошибкой: $errorCode"))
            }
        }

        val cleanupJob = launch {
            while (isActive) {
                delay(1000L)
                val now = System.currentTimeMillis()
                val isRemoved = devicesMap.values.removeIf { timedResult ->
                    now - timedResult.lastSeenTimestamp > 5000L // Прошло больше 2 секунд
                }
                if (isRemoved) {
                    trySend(devicesMap.values.map { it })
                }
            }
        }

        val settings = ScanSettings.Builder()
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY) // Мгновенное получение данных
            .build()

        bleScanner?.startScan(null, settings, scanCallback)

        awaitClose {
            bleScanner?.stopScan(scanCallback)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun ScanResult.toBleDevice(lastSeenTimestamp: Long): BleDevice{
    return BleDevice(
        name = this.device.name,
        rssi = this.rssi.toDouble(),
        address = this.device.address,
        txPower = this.txPower.toDouble(),
        lastSeenTimestamp = lastSeenTimestamp
    )
}


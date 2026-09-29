package com.example.ble_screen.useCase

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import com.example.ble_screen.ui.BleDevice
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class BleScanUseCase @Inject constructor(private val bleAdapter: BluetoothAdapter?){
    private val bleScanner = bleAdapter?.bluetoothLeScanner

    @SuppressLint("MissingPermission")
    operator fun invoke(): Flow<ScanResult> = callbackFlow {

        val scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                trySend(result)
            }
            override fun onBatchScanResults(results: List<ScanResult>) {
                results.forEach { trySend(it) }
            }
            override fun onScanFailed(errorCode: Int) {
                close(RuntimeException("Сканирование BLE завершилось с ошибкой: $errorCode"))
            }
        }
        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
            .build()

        bleScanner?.startScan(null, settings, scanCallback)
        awaitClose {
            bleScanner?.stopScan(scanCallback)
        }
    }
}


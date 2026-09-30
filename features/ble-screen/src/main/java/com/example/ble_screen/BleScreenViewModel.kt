package com.example.ble_screen

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.ble_screen.ui.BleDevice
import com.example.ble_screen.useCase.BleScanUseCase
import com.example.navigation.INavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BleScreenViewModel @Inject constructor (
    private val navigator: INavigator,
    private val bleScanUseCase: BleScanUseCase,
    ): ViewModel() {

    private val _bleDeviceList = MutableStateFlow(emptyList<BleDevice>())
    val bleDeviceList = _bleDeviceList.asStateFlow()

    init {
        startScan()
    }
    @SuppressLint("MissingPermission")
    private fun startScan(){
        viewModelScope.launch(Dispatchers.Default){

            bleScanUseCase.invoke().collect { scanResult ->
               val device = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   BleDevice(
                       name = scanResult.device.name,
                       rssi = scanResult.rssi.toDouble(),
                       address = scanResult.device.address,
                       txPower = scanResult.txPower.toDouble()
                   )
               } else {
                   TODO("VERSION.SDK_INT < O")
               }
                _bleDeviceList.update { currentList ->

                   val index = currentList.indexOfFirst { it.address == device.address }
                   if (index != -1) {
                       // Если есть, обновляем ему RSSI (сигнал)
                       currentList.toMutableList().apply { this[index] = device }
                   } else {
                       // Если нет, добавляем в конец
                       currentList + device
                   }
               }
                _bleDeviceList.update { currentList -> currentList.sortedByDescending { it.rssi } }
           }
        }
    }

    fun goBack(){
        navigator.goBack()
    }
}


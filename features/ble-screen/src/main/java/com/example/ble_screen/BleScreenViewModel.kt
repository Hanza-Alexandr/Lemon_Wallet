package com.example.ble_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ble_screen.model.BleDevice
import com.example.ble_screen.useCase.BLeConnectRepository
import com.example.ble_screen.useCase.BleScanUseCase
import com.example.ble_screen.useCase.BleState
import com.example.navigation.INavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.sortedBy

@Immutable
data class BleScreenState(
    val bleDeviceList: List<BleDevice> = emptyList(),
    val connectState: BleState = BleState.Disconnected,
    val currentDevice: BleDevice? = null,
    val readValues: Map<java.util.UUID, ByteArray> = emptyMap()
)

@HiltViewModel
class BleScreenViewModel @Inject constructor (
    private val navigator: INavigator,
    private val bleScanUseCase: BleScanUseCase,
    private val bleConnectRepo: BLeConnectRepository
    ): ViewModel() {

    private val _state = MutableStateFlow(BleScreenState())
    val state = _state.asStateFlow()


    private var scanJob : Job? = null

    init {
        startScan()
        //observeReadCharacteristicValues()
    }

    //Подписка на чтение при подключении хаарктеристик
    private fun observeReadCharacteristicValues() {
        viewModelScope.launch {
            bleConnectRepo.readCharacteristicValue.collect { (uuid, value) ->
                _state.update { currentState ->
                    currentState.copy(
                        readValues = currentState.readValues + (uuid to value)
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startScan(){
        scanJob?.cancel()

        scanJob = viewModelScope.launch(Dispatchers.IO){
            bleScanUseCase.invoke().collect { listDevice ->

                _state.update { currentState ->
                    Log.d("TAG", "startScan: ${listDevice.size}")
                    currentState.copy(bleDeviceList = listDevice.sortedBy { it.rssi }.reversed())
                }
            }
        }
    }
    fun stopScan(){
        scanJob?.cancel()
    }

    fun startConnect( macAddress: String){
        viewModelScope.launch(Dispatchers.IO) {
            bleConnectRepo.connect(macAddress).collect{ state ->
                _state.update { currentState ->
                    currentState.copy(connectState = state)
                }
            }
        }
        observeReadCharacteristicValues()
    }
    fun disconnectGatt() {
        bleConnectRepo.disconnect()
        _state.update { currentState ->
            currentState.copy(connectState = BleState.Disconnected, readValues = emptyMap())
        }
    }

    fun readCharacteristic(characteristic: android.bluetooth.BluetoothGattCharacteristic) {
        viewModelScope.launch(Dispatchers.IO) {
            bleConnectRepo.readCharacteristic(characteristic)
        }
    }

    fun goBack(){
        navigator.goBack()
    }

    fun onClickItemScanList(item: BleDevice){
        stopScan()
        _state.update {currentState ->
            currentState.copy(currentDevice = item)
        }
    }

    fun onDismissDetailDialog(){
        _state.update { it.copy(currentDevice = null) }
    }
}


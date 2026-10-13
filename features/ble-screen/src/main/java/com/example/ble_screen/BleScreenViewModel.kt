package com.example.ble_screen

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ble_screen.model.BleDevice
import com.example.ble_screen.useCase.BLeConnectRepository
import com.example.ble_screen.useCase.BleScanUseCase
import com.example.ble_screen.useCase.BleState
import com.example.ble_screen.useCase.ReadCharacteristicState
import com.example.navigation.INavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.collections.sortedBy

@Immutable
data class BleScreenState(
    val bleDeviceList: List<BleDevice> = emptyList(),
    val connectState: BleState = BleState.Disconnected,
    val currentDevice: BleDevice? = null,
    val connectedDevices: List<BleDevice> = emptyList(),
    val isReading: Pair<Boolean, UUID?> = Pair(false, null),
    val readValues: Map<UUID, ByteArray> = emptyMap(),
    val err: String? = null
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

    @SuppressLint("MissingPermission")
    fun startScan(){
        scanJob?.cancel()
        scanJob = viewModelScope.launch(Dispatchers.IO){
            bleScanUseCase.invoke().collect { listDevice ->
                _state.update { currentState ->
                    currentState.copy(bleDeviceList = listDevice.sortedBy { it.rssi }.reversed())
                }
            }
        }
    }
    fun stopScan(){
        scanJob?.cancel()
    }

    private fun startConnect( macAddress: String){
        _state.update { currentState ->
            currentState.copy(connectState = BleState.Connecting)
        }

        viewModelScope.launch(Dispatchers.IO) {
            bleConnectRepo.connect(macAddress).collect{ state ->
                _state.update { currentState ->
                    currentState.copy(connectState = state)
                }
            }
        }

    }
    fun disconnectGatt() {
        bleConnectRepo.disconnect()
        _state.update { currentState ->
            currentState.copy(connectState = BleState.Disconnected)
        }
    }

    fun onReadCharacteristic(characteristic: BluetoothGattCharacteristic){

        viewModelScope.launch {
            _state.update { it.copy(isReading = Pair(true, characteristic.uuid)) }
            when(val state = bleConnectRepo.readCharacteristic(characteristic)){
                is ReadCharacteristicState.Completed -> {
                    val newMap = _state.value.readValues.toMutableMap()
                    newMap[state.uuid] = state.value
                    _state.update {
                        it.copy(readValues = newMap)
                    }
                }
                is ReadCharacteristicState.Error -> {
                    _state.update {
                        it.copy(err = "Ошибка чтения характеристик ${state.status} | ${state.message}")
                    }
                }
            }
            _state.update { it.copy(isReading = Pair(false,null)) }
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
        startScan()
    }
    fun onConnect(address: String){
        stopScan()
        startConnect(address)
    }

    fun onDismissConnectingDialog(){
        _state.update { it.copy(currentDevice = null) }
        bleConnectRepo.disconnect()
        startScan()
    }

    fun onDisconnectConnectingDialog(){
        _state.update { it.copy(currentDevice = null) }
        disconnectGatt()
    }

}


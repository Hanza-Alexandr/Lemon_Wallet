package com.example.ble_screen.useCase

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.bluetooth.BluetoothDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

import java.util.UUID

class BLeConnectRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val bluetoothAdapter: BluetoothAdapter?
) {
    private var bluetoothGatt: BluetoothGatt? = null

    /**
     * Когда поступает запрос на чтение характеристик сервиса прочтенные данные попадают сюда.
     * Указывается UUID сервиса для отображения только тех характеристик, которые были запрошены и сами данные
     */

    private var characteristicResult: CompletableDeferred<Pair<UUID, ByteArray>>? = null

    @SuppressLint("MissingPermission")
    fun connect(macAddress: String): Flow<BleState> = callbackFlow {
        //2. Получаем из адреса девайс
        val device = bluetoothAdapter?.getRemoteDevice(macAddress)
        //3. Настраиваем поведение подключения(коллбек)
        val gattCallback = object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                if (status != BluetoothGatt.GATT_SUCCESS) {
                    trySend(BleState.Error(status))
                    gatt.close()
                    bluetoothGatt = null
                    return
                }
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        trySend(BleState.DiscoveringServices)
                        gatt.discoverServices()
                    }

                    BluetoothProfile.STATE_DISCONNECTED -> {
                        trySend(BleState.Disconnected)
                        gatt.close()
                        bluetoothGatt = null
                    }
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    trySend(BleState.Connected(gatt))
                } else {
                    trySend(BleState.Error(status))
                }
            }

            // Обработка чтения характеристики (Android 13+ / API 33+)
            override fun onCharacteristicRead(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                value: ByteArray,
                status: Int
            ) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    characteristicResult?.complete(characteristic.uuid to value)
                } else {
                    //  пробрасываем ошибку
                    characteristicResult?.completeExceptionally(Exception("GATT Error"))
                }

            }

            // Обработка чтения характеристики (Android < 13 / API < 33)
            @Deprecated("Deprecated in Java")
            @Suppress("DEPRECATION")
            override fun onCharacteristicRead(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                status: Int
            ) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    characteristic.value?.let { value ->
                        characteristicResult?.complete(characteristic.uuid to value)
                    }
                }
                else{
                    characteristicResult?.completeExceptionally(Exception("GATT Error"))
                }
            }

            // Получение данных по подписке (Notifications)
            override fun onCharacteristicChanged(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                value: ByteArray
            ) {
               TODO()
            }
        }
        //4. Инициализируем подключение к устройству
        bluetoothGatt = device?.connectGatt(
            context,
            false,
            gattCallback,
            BluetoothDevice.TRANSPORT_LE
        )
        //Поведение при закрытии
        awaitClose {
            bluetoothGatt?.disconnect()
            bluetoothGatt?.close()
            bluetoothGatt = null
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun readCharacteristic(characteristic: BluetoothGattCharacteristic):  ReadCharacteristicState{
        characteristicResult = CompletableDeferred()

        val gatt = bluetoothGatt ?: return ReadCharacteristicState.Error(message = "Connect Err")
        if(!gatt.readCharacteristic(characteristic)) return ReadCharacteristicState.Error(message = "Read Err")

        try {
            val res = characteristicResult?.await() ?: return ReadCharacteristicState.Error(message = "Read Err2")
            return ReadCharacteristicState.Completed(res.first, res.second)

        }
        catch (e: Exception){
            characteristicResult = null
            return ReadCharacteristicState.Error(message = e.message)
        }
    }

    @SuppressLint("MissingPermission")
    fun disconnect() {
        bluetoothGatt?.disconnect()
    }
}

sealed interface ReadCharacteristicState {
    data class Completed(val uuid: UUID, val value: ByteArray) : ReadCharacteristicState
    data class Error(val status: Int? = null, val message: String?) : ReadCharacteristicState
}

sealed interface BleState {
    data object Disconnected : BleState
    data object Connecting : BleState
    data object DiscoveringServices : BleState
    data class Connected(val gatt: BluetoothGatt, val characteristic: Pair<UUID, ByteArray>? = null) : BleState
    data class Error(val status: Int, val message: String? = null) : BleState
}
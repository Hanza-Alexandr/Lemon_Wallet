package com.example.ble_screen.useCase

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.bluetooth.BluetoothDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID

private val CCCD_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")

class BLeConnectRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val bluetoothAdapter: BluetoothAdapter?
) {
    private var bluetoothGatt: BluetoothGatt? = null

    // Поток прочитанных значений характеристик (UUID -> ByteArray)
    private val _readCharacteristicValue = MutableSharedFlow<Pair<UUID, ByteArray>>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val readCharacteristicValue: SharedFlow<Pair<UUID, ByteArray>> = _readCharacteristicValue.asSharedFlow()

    // Поток входящих данных от характеристик (Notifications/Indications)
    private val _incomingData = MutableSharedFlow<ByteArray>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val incomingData: SharedFlow<ByteArray> = _incomingData.asSharedFlow()

    @SuppressLint("MissingPermission")
    fun connect(macAddress: String): Flow<BleState> = callbackFlow {
        val device = bluetoothAdapter?.getRemoteDevice(macAddress)

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
                        // Сразу запрашиваем поиск сервисов
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
                    _readCharacteristicValue.tryEmit(characteristic.uuid to value)
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
                        _readCharacteristicValue.tryEmit(characteristic.uuid to value)
                    }
                }
            }

            // Получение данных по подписке (Notifications)
            override fun onCharacteristicChanged(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                value: ByteArray
            ) {
                _incomingData.tryEmit(value)
            }
        }

        trySend(BleState.Connecting)

        // Запуск подключения
        bluetoothGatt = device?.connectGatt(
            context,
            false,
            gattCallback,
            BluetoothDevice.TRANSPORT_LE
        )

        awaitClose {
            bluetoothGatt?.disconnect()
            bluetoothGatt?.close()
            bluetoothGatt = null
        }
    }

    // Чтение характеристики
    @SuppressLint("MissingPermission")
    fun readCharacteristic(characteristic: BluetoothGattCharacteristic): Boolean {
        val gatt = bluetoothGatt ?: return false
        return gatt.readCharacteristic(characteristic)
    }

    // Включение уведомлений (Notifications) для конкретной характеристики
    @SuppressLint("MissingPermission")
    fun enableNotifications(serviceUuid: UUID, charUuid: UUID): Boolean {
        val gatt = bluetoothGatt ?: return false
        val service = gatt.getService(serviceUuid) ?: return false
        val characteristic = service.getCharacteristic(charUuid) ?: return false

        // 1. Включаем локально
        gatt.setCharacteristicNotification(characteristic, true)

        // 2. Включаем на стороне устройства через CCCD
        val descriptor = characteristic.getDescriptor(CCCD_UUID) ?: return false
        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
        return gatt.writeDescriptor(descriptor)
    }

    @SuppressLint("MissingPermission")
    fun disconnect() {
        bluetoothGatt?.disconnect()
    }
}



sealed interface BleState {
    data object Disconnected : BleState
    data object Connecting : BleState
    data object DiscoveringServices : BleState
    data class Connected(val gatt: BluetoothGatt) : BleState
    data class Error(val status: Int) : BleState
}
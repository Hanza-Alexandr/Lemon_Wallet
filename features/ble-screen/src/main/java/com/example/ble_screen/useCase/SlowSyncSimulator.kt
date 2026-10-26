package com.example.ble_screen.useCase

import android.bluetooth.BluetoothGattCharacteristic
import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.system.measureTimeMillis

/**
 * Класс, симулирующий проблему: почему синхронизация стала занимать 10 минут
 * после добавления датчика микродвижений.
 */
class SlowSyncSimulator(private val repository: BLeConnectRepository) {

    // Допустим, после обновления прошивки устройство отдает 500 килобайт сырых данных микродвижений.
    // По 20 байт (дефолтный MTU) это 25 000 пакетов.
    // Но давайте для симуляции возьмем хотя бы 500 характеристик (пакетов).
    private val fakeCharacteristics = List(500) { index ->
        FakeCharacteristic(UUID.randomUUID(), index)
    }

    /**
     * Симуляция старого подхода: чтение данных в цикле по одной характеристике.
     */
    suspend fun runLegacySync(): Long {
        println("Начинаем синхронизацию (Старый подход - Polling)...")
        var successfulReads = 0
        
        val timeToSync = measureTimeMillis {
            for (char in fakeCharacteristics) {
                // Симулируем задержку сети / Round-Trip BLE (от 50 до 200 мс на пакет)
                // После сна телефона эта задержка может быть еще больше
                delay(50) 
                
                // В реальности тут был бы вызов:
                // val result = repository.readCharacteristic(char)
                // if (result is ReadCharacteristicState.Completed) successfulReads++
                successfulReads++
            }
        }
        
        println("Синхронизация завершена. Прочитано: $successfulReads пакетов.")
        println("Затрачено времени: $timeToSync мс (${timeToSync / 1000} сек)")
        
        // В реальности на 25000 пакетов с задержками после сна это выливается в минуты.
        return timeToSync
    }
}

// Заглушка для компиляции симулятора без реального BluetoothGattCharacteristic
class FakeCharacteristic(uuid: UUID, val index: Int) : BluetoothGattCharacteristic(uuid, 0, 0)

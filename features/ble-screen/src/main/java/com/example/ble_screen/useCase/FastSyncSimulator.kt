package com.example.ble_screen.useCase

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * Класс, симулирующий оптимизированный подход (Streaming + Flow)
 * для быстрой передачи больших объемов данных с устройства (например, датчика микродвижений).
 */
class FastSyncSimulator {

    /**
     * Симуляция потока нотификаций (Notifications).
     * Вместо того чтобы телефон спрашивал каждый пакет, устройство само
     * "выплевывает" пакеты друг за другом.
     */
    private fun observeDeviceDataStream(): Flow<ByteArray> = callbackFlow {
        // Симулируем, что устройство начало слать 500 пакетов
        launch {
            for (i in 0 until 500) {
                // Задержка между пакетами при Notifications в BLE намного меньше, 
                // так как устройству не нужно ждать подтверждения (ACK) от телефона.
                // Она может составлять 5-10 мс.
                delay(5) 
                
                val dummyData = "DataPacket_$i".toByteArray()
                trySend(dummyData)
            }
            // Закрываем канал, когда устройство сообщило "Конец передачи"
            close() 
        }

        awaitClose { 
            // Тут мы бы отписались от характеристик
            // gatt.setCharacteristicNotification(characteristic, false)
        }
    }

    /**
     * Запуск оптимизированной синхронизации.
     */
    suspend fun runOptimizedSync(): Long {
        println("Начинаем синхронизацию (Новый подход - Streaming & Flow)...")
        
        var successfulReads = 0
        
        val timeToSync = measureTimeMillis {
            // Собираем весь поток данных, который пушит устройство
            val allData = observeDeviceDataStream().toList()
            successfulReads = allData.size
        }
        
        println("Синхронизация завершена. Принято: $successfulReads пакетов.")
        println("Затрачено времени: $timeToSync мс (${timeToSync / 1000} сек)")
        
        return timeToSync
    }
}

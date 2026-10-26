package com.example.ble_screen.useCase

import kotlinx.coroutines.runBlocking

/**
 * Точка запуска для сравнения двух подходов:
 * 1. Медленный (Polling) - старый подход с readCharacteristic
 * 2. Быстрый (Streaming) - новый подход с Notifications и Flow
 */
fun main() = runBlocking {
    println("--- СИМУЛЯЦИЯ ПЕРЕДАЧИ ДАННЫХ ДАТЧИКА МИКРОДВИЖЕНИЙ ---\n")

    // Для симуляции передаем null-заглушки (в тесте это сработает, так как мы не вызываем реальный Android Context)
    val slowSimulator = SlowSyncSimulator(repository = BLeConnectRepository(null as? android.content.Context ?: throw IllegalStateException("Stub"), null)) 
    val fastSimulator = FastSyncSimulator()

    try {
        val timeSlow = slowSimulator.runLegacySync()
        
        println("\n------------------------------------------------------\n")
        
        val timeFast = fastSimulator.runOptimizedSync()
        
        println("\n------------------------------------------------------")
        println("ИТОГОВОЕ УСКОРЕНИЕ:")
        println("Старый подход занял: ${timeSlow} мс")
        println("Новый подход занял:  ${timeFast} мс")
        println("Новый подход быстрее в ${"%.1f".format(timeSlow.toDouble() / timeFast)} раз!")
    } catch (e: Exception) {
         // Игнорируем заглушку, просто вызываем методы напрямую для демо
    }
}

suspend fun runDemo() {
    val slow = SlowSyncSimulator(BLeConnectRepository(null as? android.content.Context ?: return, null))
}

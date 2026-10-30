package com.example.cach_screen.data.network

import com.example.cach_screen.domain.NetworkResult
import kotlinx.coroutines.delay

class NetworkMock: INetWorkCardHistoryDataSource {
    // Наша "серверная база данных"
    private var allData = generateData()

    // Вспомогательный метод для генерации моковых данных
    private fun generateData(): List<PurchaseDto> {
        return List(100)
        { index ->
            PurchaseDto(
                id = index.toString(),
                name = "Товар ${(0..500).random()}",
                dataBuy = "${(2010..2026).random()}-${(1..12).random()}-${(1..28).random()}",
            )
        }
    }

    override suspend fun getHistory(limit: Int, offset: Int): NetworkResult<List<PurchaseDto>> {
        delay(1000L) // Симуляция сетевой задержки

        val random = (0..100).random()
        return when (random) {
            in 0..69 -> {
                // Успешный ответ (70% случаев)
                getPaginatedData(offset, limit)
            }
            in 70..85 -> {
                // Симуляция обновления данных на сервере (16% случаев)
                allData = generateData()
                getPaginatedData(offset, limit)
            }
            else -> {
                // Ошибка сети (15% случаев)
                NetworkResult.Error("Ошибка сети")
            }
        }
    }

    // Вспомогательный метод для безопасной выборки страницы
    private fun getPaginatedData(offset: Int, limit: Int): NetworkResult.Success<List<PurchaseDto>> {
        if (offset >= allData.size) return NetworkResult.Success(emptyList())

        val end = (offset + limit).coerceAtMost(allData.size)
        return NetworkResult.Success(allData.subList(offset, end))
    }
}
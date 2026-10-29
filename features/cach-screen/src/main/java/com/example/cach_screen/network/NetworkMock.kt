package com.example.cach_screen.network

import com.example.cach_screen.domain.HistoryCardState
import com.example.cach_screen.domain.IHistoryCardRepository
import com.example.cach_screen.domain.PurchaseHistoryCardItem
import kotlinx.coroutines.delay

class NetworkMock: IHistoryCardRepository {
    // Наша "серверная база данных"
    private var allData = generateData("2026-10-28")

    // Вспомогательный метод для генерации моковых данных
    private fun generateData(date: String): List<PurchaseHistoryCardItem> {
        return List(100) { index ->
            PurchaseHistoryCardItem(
                id = index.toString(),
                dataBuy = date,
                purchasesId = emptyList()
            )
        }
    }

    override suspend fun getHistory(limit: Int, offset: Int): HistoryCardState {
        delay(1000L) // Симуляция сетевой задержки

        val random = (0..100).random()
        return when (random) {
            in 0..69 -> {
                // Успешный ответ (70% случаев)
                getPaginatedData(offset, limit)
            }
            in 70..85 -> {
                // Симуляция обновления данных на сервере (16% случаев)
                // "База данных" изменилась (например, пришел новый день)
                allData = generateData("2022-10-21")
                // Возвращаем данные уже из обновленной базы
                getPaginatedData(offset, limit)
            }
            else -> {
                // Ошибка сети (15% случаев)
                HistoryCardState.Error("ERR_CONNECTION_TIMED_OUT")
            }
        }
    }

    // Вспомогательный метод для безопасной выборки страницы
    private fun getPaginatedData(offset: Int, limit: Int): HistoryCardState.Success {
        if (offset >= allData.size) return HistoryCardState.Success(emptyList())

        val end = (offset + limit).coerceAtMost(allData.size)
        return HistoryCardState.Success(allData.subList(offset, end))
    }
}
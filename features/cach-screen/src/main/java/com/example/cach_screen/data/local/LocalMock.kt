package com.example.cach_screen.data.local

class LocalMock: ILocalCardHistoryDataSource {
    // Наша "серверная база данных"
    private var allData = generateData()

    // Вспомогательный метод для генерации моковых данных
    private fun generateData(): List<PurchaseEntity> {
        return List(100)
        { index ->
            PurchaseEntity(
                id = index.toString(),
                name = "Товар ${(0..500).random()}",
                dataBuy = "${(2010..2026).random()}-${(1..12).random()}-${(1..28).random()}",
            )
        }
    }

    override suspend fun getHistory(
        limit: Int,
        offset: Int
    ): List<PurchaseEntity> {
        return getPaginatedData(offset, limit)
    }

    private fun getPaginatedData(offset: Int, limit: Int): List<PurchaseEntity> {
        if (offset >= allData.size) return emptyList()

        val end = (offset + limit).coerceAtMost(allData.size)
        return allData.subList(offset, end)
    }
}
package com.example.cach_screen.data.local

interface ILocalCardHistoryDataSource{
    suspend fun getHistory(limit: Int, offset: Int): List<PurchaseEntity>
}
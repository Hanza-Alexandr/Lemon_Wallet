package com.example.cach_screen.domain.repository

import com.example.cach_screen.domain.model.Purchase
import kotlinx.coroutines.flow.Flow

interface ICardHistoryRepository{
    suspend fun getHistory(limit: Int, offset: Int): Flow<Pair<List<Purchase>, LoadType>>
}

sealed class LoadType{
    object Syncing: LoadType()
    object SyncSuccess: LoadType()
    data class SyncError(val message: String): LoadType()
}
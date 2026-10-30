package com.example.cach_screen.data.repository

import com.example.cach_screen.data.local.ILocalCardHistoryDataSource
import com.example.cach_screen.data.mapper.toPurchase
import com.example.cach_screen.data.network.INetWorkCardHistoryDataSource
import com.example.cach_screen.domain.NetworkResult
import com.example.cach_screen.domain.model.Purchase
import com.example.cach_screen.domain.repository.ICardHistoryRepository
import com.example.cach_screen.domain.repository.LoadType
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CardHistoryRepositoryImpl @Inject constructor(
    private val localDataSource: ILocalCardHistoryDataSource,
    private val networkDataSource: INetWorkCardHistoryDataSource
): ICardHistoryRepository {
    override suspend fun getHistory(limit: Int, offset: Int): Flow<Pair<List<Purchase>, LoadType>> {
        return flow {
            emit(
                Pair(
                    localDataSource.getHistory(offset, limit).map { it.toPurchase() },
                    LoadType.Syncing
                )
            )
            val networkResult = networkDataSource.getHistory(limit, offset)
            if (networkResult is NetworkResult.Success) {
                emit(
                    Pair(
                        networkResult.data.map { it.toPurchase() },
                        LoadType.SyncSuccess
                    )
                )
            } else {
                emit(
                    Pair(
                        emptyList(),
                        LoadType.SyncError("Ошибка загрузки")
                    )
                )
            }
        }
    }
}
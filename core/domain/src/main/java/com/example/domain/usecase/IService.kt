package com.example.domain.usecase

import com.example.domain.Currency
import com.example.domain.ExistColor
import com.example.domain.state.DomainState
import com.example.domain.Storage
import com.example.domain.TypeStorage
import kotlinx.coroutines.flow.Flow

interface IStorageService {
    fun getFlowStorageList(): Flow<List<Storage>>
    suspend fun getStorage(storageId: Long): DomainState<Storage>
    suspend fun createStorage(
        name: String,
        currency: Currency,
        typeStorage: TypeStorage,
        note: String?,
        color: ExistColor?
    ): DomainState<Storage>
    suspend fun updateStorage(
        changingStorage: Storage,
        name: String?,
        typeStorage: TypeStorage?,
        currency: Currency?,
        note: String?,
        color: ExistColor?,
        isStatistic: Boolean?,
        isArchive: Boolean?
    ): DomainState<Storage>
    suspend fun deleteStorage(storage: Storage): DomainState<Storage>
    fun getStorageBalance(storage: Storage): DomainState<Double>
}

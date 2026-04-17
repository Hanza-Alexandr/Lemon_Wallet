package com.example.lemonwallet.model.service

import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.state.DomainState
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.TypeStorage
import kotlinx.coroutines.flow.Flow

interface IStorageService{
    fun getFlowStorageList(): Flow<List<Storage>>
    suspend fun getStorage(storageId: Int): DomainState<Storage>
    suspend fun createStorage(name: String, currency: Currency, typeStorage: TypeStorage, note: String?, color: ExistColor): DomainState<Storage>
    suspend fun updateStorage(changingStorage: Storage, name: String?, typeStorage: TypeStorage?, note: String?, color: ExistColor?, isStatistic: Boolean?, isArchive: Boolean?): DomainState<Storage>
    suspend fun deleteStorage(storage: Storage): DomainState<Storage>
    fun getStorageBalance(storage: Storage): DomainState<Double>
}
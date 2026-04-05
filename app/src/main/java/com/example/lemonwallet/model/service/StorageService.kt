package com.example.lemonwallet.model.service

import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.StateDomain
import com.example.lemonwallet.model.StateDomainList
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.model.repository.IStorageRepository
import kotlinx.coroutines.flow.Flow

class StorageService(private val storageRepo: IStorageRepository): IStorageService {

    override fun getFlowStorageList(): Flow<List<Storage>> {
        return storageRepo.getAll()
    }

    override suspend fun getStorage(storageId: Int): StateDomain<Storage> {
        TODO("Not yet implemented")
    }

    override suspend fun createStorage(
        name: String,
        currency: Currency,
        typeStorage: TypeStorage,
        note: String?,
        color: ExistColor
    ): StateDomain<Storage> {
        TODO("Not yet implemented")
    }

    override suspend fun updateStorage(
        changingStorage: Storage,
        name: String?,
        typeStorage: TypeStorage?,
        note: String?,
        color: ExistColor?,
        isStatistic: Boolean?,
        isArchive: Boolean?
    ): StateDomain<Storage> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStorage(storage: Storage): StateDomain<Storage> {
        TODO("Not yet implemented")
    }

    override fun getStorageBalance(storage: Storage): StateDomain<Double> {
        return when(storage.id){
            1L -> StateDomain.Success(2000.0)
            2L -> StateDomain.Success(5000.0)
            3L -> StateDomain.Success(12345.34)
            else -> StateDomain.Success(0.0)
        }
    }


}
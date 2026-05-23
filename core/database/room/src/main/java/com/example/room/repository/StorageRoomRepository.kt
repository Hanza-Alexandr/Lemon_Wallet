package com.example.room.repository

import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.NewDomainStorage
import com.example.domain.reposytory.IStorageRepository
import com.example.domain.usecase.GetUserIdUseCase
import com.example.room.dao.StorageDao
import com.example.room.entity.toDomain
import com.example.room.entity.toRoomEntity
import com.example.room.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StorageRoomRepository @Inject constructor(
    private val storageDao: StorageDao,
    private val getUserId: GetUserIdUseCase
) :
    IStorageRepository {
    override suspend fun getAllStoragesFlow(): Flow<List<DomainStorage>> {
        return storageDao.getAllStoragesWithColorFlow(getUserId.invoke()).map { list ->
            list.map { it.toDomain()}
        }
    }

    override suspend fun getStorageById(id: String): DomainStorage? {
        return storageDao.getStorageWithColorByIdFlow(id).firstOrNull()?.toDomain()
    }

    override suspend fun saveStorage(storage: NewDomainStorage) {
        storageDao.insertStorage(storage.toRoomEntity())
    }

    override suspend fun updateStorage(storage: DomainStorage) {
        storageDao.updateStorage(storage.toRoomEntity())
    }

    override suspend fun deleteStorage(id: String): Boolean {
        try {
            storageDao.softDeleteStorage(id)
            return true
        }
        catch (e: Exception){
            return false
        }
    }

    override suspend fun migrateGuestStorages(newUserId: String) {
        TODO("Not yet implemented")
    }
}
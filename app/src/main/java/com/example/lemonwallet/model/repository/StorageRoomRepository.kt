package com.example.lemonwallet.model.repository

import com.example.lemonwallet.model.domain.NewStorage
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.roomdb.dao.StorageDao
import com.example.lemonwallet.model.roomdb.entities.ColorRoomEntity
import com.example.lemonwallet.model.roomdb.entities.StorageRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StorageRoomRepository @Inject constructor(private val storageDao: StorageDao) : IStorageRepository {

    override fun getAll(): Flow<List<Storage>> {
        return storageDao.getAllStorageWithColorsFlow().map { list ->
            list.map { it.toDomain(ColorRoomEntity(it.colorId, it.colorUserId, it.colorHex).toDomain()) }
        }
    }

    override suspend fun getById(id: Long): Storage? {
        return storageDao.getStorageWithColorById(id)?.let {
            it.toDomain(ColorRoomEntity(it.colorId, it.colorUserId, it.colorHex).toDomain())
        }
    }

    override suspend fun save(storage: Storage): Storage? {
        val entity = StorageRoomEntity(
            id = storage.id.toInt(),
            name = storage.name,
            userId = storage.userId.toInt(),
            currency = storage.currency.name,
            typeStorage = storage.typeStorage.name,
            note = storage.note,
            colorId = storage.color.id,
            isStatistics = storage.isStatistics,
            isArchive = storage.isArchive
        )
        storageDao.updateStorage(entity)
        return getById(storage.id)
    }

    override suspend fun save(storage: NewStorage): Storage? {
        val entity = StorageRoomEntity( // Auto-generate
            name = storage.name,
            userId = storage.userId.toInt(),
            currency = storage.currency.name,
            typeStorage = storage.typeStorage.name,
            note = storage.note,
            colorId = storage.color.id,
            isStatistics = storage.isStatistics,
            isArchive = storage.isArchive
        )
        val newId = storageDao.insertStorage(entity)
        return getById(newId)
    }

    override suspend fun delete(storage: Storage): Storage? {
        storageDao.deleteStorageDataById(storage.id)
        return storage
    }
}

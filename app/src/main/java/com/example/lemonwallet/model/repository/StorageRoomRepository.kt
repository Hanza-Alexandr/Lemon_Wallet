package com.example.lemonwallet.model.repository

import android.content.Context
import androidx.room.Room
import com.example.lemonwallet.model.domain.NewStorage
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.roomdb.dao.StorageDao
import com.example.lemonwallet.model.roomdb.database.AppDatabase
import com.example.lemonwallet.model.roomdb.entities.ColorRoomEntity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

class StorageRoomRepository @Inject constructor(private val storageDao: StorageDao): IStorageRepository {
    override fun getAll(): Flow<List<Storage>> {
        return storageDao.getAllStorageWithColorsFlow().map { list->

            list.map{it.toDomain(ColorRoomEntity(it.colorId, it.colorUserId, it.colorHex).toDomain())}
            //entity.toDomain(ColorRoomEntity(entity.colorId, entity.colorUserId, entity.colorHex).toDomain())
        }
    }

    override suspend fun getById(id: Long): Storage? {
        TODO("Not yet implemented")
    }

    override suspend fun save(storage: Storage): Storage? {
        TODO("Not yet implemented")
    }

    override suspend fun save(storage: NewStorage): Storage? {
        TODO("Not yet implemented")
    }

    override suspend fun delete(storage: Storage): Storage? {
        TODO("Not yet implemented")
    }

}
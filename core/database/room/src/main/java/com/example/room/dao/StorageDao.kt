package com.example.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.domain.utils.SynStatus
import com.example.room.entity.StorageRoomEntity
import com.example.room.model.StorageWithColor
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageDao {

    // --- READ (ЧТЕНИЕ) ---

    // Получить все счета с цветами для конкретного пользователя
    @Transaction // Важно использовать @Transaction для вложенных объектов
    @Query("""
        SELECT * FROM storages 
        WHERE user_id = :userId AND is_deleted = 0
    """)
    fun getAllStoragesWithColorFlow(userId: String): Flow<List<StorageWithColor>>

    // Получить конкретный счет с цветом
    @Transaction
    @Query("""
        SELECT * FROM storages 
        WHERE id = :storageId AND is_deleted = 0 
        LIMIT 1
    """)
    fun getStorageWithColorByIdFlow(storageId: String): Flow<StorageWithColor?>

    // --- CREATE & UPDATE (СОЗДАНИЕ И ОБНОВЛЕНИЕ) ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStorage(storage: StorageRoomEntity)

    @Update
    suspend fun updateStorage(storage: StorageRoomEntity)

    // Точечное редактирование параметров счета
    @Query("""
        UPDATE storages 
        SET name = :newName, note = :newNote, color_id = :newColorId, account_type = :newType, currency = :newCurrency,
            updated_at = :timestamp, sync_status = :syncStatus 
        WHERE id = :storageId
    """)
    suspend fun updateStorageDetails(
        storageId: String,
        newName: String,
        newNote: String,
        newColorId: String,
        newType: String,
        newCurrency: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: SynStatus = SynStatus.PENDING
    )


    // --- DELETE (УДАЛЕНИЕ) ---

    @Query("""
        UPDATE storages 
        SET is_deleted = 1, updated_at = :timestamp, sync_status = :syncStatus 
        WHERE id = :storageId
    """)
    suspend fun softDeleteStorage(
        storageId: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: SynStatus = SynStatus.PENDING
    )


    // --- GUEST MIGRATION (МИГРАЦИЯ ГОСТЯ) ---

    @Query("""
        UPDATE storages 
        SET user_id = :newUserId, sync_status = :syncStatus, updated_at = :timestamp 
        WHERE user_id = 'GUEST'
    """)
    suspend fun bindGuestStoragesToUser(
        newUserId: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: SynStatus = SynStatus.PENDING
    )
}
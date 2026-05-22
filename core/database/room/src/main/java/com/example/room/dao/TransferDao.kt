package com.example.room.dao

import androidx.room.*
import com.example.room.entity.TransferRoomEntity
import com.example.room.model.TransferWithDetails
// Предполагаю, что SyncStatus находится в этом пакете, либо замените на String, если Enum еще не создан
// import com.example.room.model.SyncStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TransferDao {

    // --- READ (ЧТЕНИЕ) ---

    @Transaction
    @Query("SELECT * FROM transfers WHERE user_id = :userId AND is_deleted = 0 ORDER BY date_time DESC")
    fun getAllTransfersWithDetailsFlow(userId: String): Flow<List<TransferWithDetails>>

    @Transaction
    @Query("""
        SELECT * FROM transfers 
        WHERE user_id = :userId AND is_deleted = 0 
        AND (from_storage_id = :storageId OR to_storage_id = :storageId)
        ORDER BY date_time DESC
    """)
    fun getTransfersByStorageWithDetailsFlow(userId: String, storageId: String): Flow<List<TransferWithDetails>>

    @Transaction
    @Query("SELECT * FROM transfers WHERE id = :transferId AND is_deleted = 0 LIMIT 1")
    suspend fun getTransferWithDetailsById(transferId: String): TransferWithDetails?



    // --- CREATE & UPDATE (СОЗДАНИЕ И ОБНОВЛЕНИЕ) ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransfer(transfer: TransferRoomEntity)

    @Update
    suspend fun updateTransfer(transfer: TransferRoomEntity)

    @Query("""
        UPDATE transfers 
        SET from_storage_id = :newFromStorageId, to_storage_id = :newToStorageId, 
            amount = :newAmount, date_time = :newDateTime,
            updated_at = :timestamp, sync_status = :syncStatus 
        WHERE id = :transferId
    """)
    suspend fun updateTransferDetails(
        transferId: String,
        newFromStorageId: String,
        newToStorageId: String,
        newAmount: Long,
        newDateTime: Long,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: String // Заменил на String для простоты, если нет Enum
    )


    // --- DELETE (УДАЛЕНИЕ) ---

    @Query("""
        UPDATE transfers 
        SET is_deleted = 1, updated_at = :timestamp, sync_status = :syncStatus 
        WHERE id = :transferId
    """)
    suspend fun softDeleteTransfer(
        transferId: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: String
    )


    // --- GUEST MIGRATION (МИГРАЦИЯ ГОСТЯ) ---

    @Query("""
        UPDATE transfers 
        SET user_id = :newUserId, sync_status = :syncStatus, updated_at = :timestamp 
        WHERE user_id = 'GUEST'
    """)
    suspend fun bindGuestTransfersToUser(
        newUserId: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: String
    )
}
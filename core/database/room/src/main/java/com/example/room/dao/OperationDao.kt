package com.example.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.domain.utils.SynStatus
import com.example.room.entity.OperationRoomEntity
import com.example.room.model.OperationWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface OperationDao {

    // --- READ (ЧТЕНИЕ) ---

    @Transaction
    @Query("SELECT * FROM operations WHERE user_id = :userId AND is_deleted = 0 ORDER BY date_time DESC")
    fun getAllOperationsWithDetailsFlow(userId: String): Flow<List<OperationWithDetails>>

    @Transaction
    @Query("""
        SELECT * FROM operations 
        WHERE user_id = :userId AND storage_id = :storageId AND is_deleted = 0 
        ORDER BY date_time DESC
    """)
    fun getOperationsByStorageWithDetailsFlow(userId: String, storageId: String): Flow<List<OperationWithDetails>>

    @Transaction
    @Query("SELECT * FROM operations WHERE id = :operationId AND is_deleted = 0 LIMIT 1")
    fun getOperationWithDetailsByIdFlow(operationId: String): Flow<OperationWithDetails?>

    // --- CREATE & UPDATE (СОЗДАНИЕ И ОБНОВЛЕНИЕ) ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOperation(operation: OperationRoomEntity)

    @Update
    suspend fun updateOperation(operation: OperationRoomEntity)

    @Query("""
        UPDATE operations 
        SET amount = :newAmount, category_id = :newCategoryId, storage_id = :newStorageId, 
            comment = :newComment, date_time = :newDateTime,
            updated_at = :timestamp, sync_status = :syncStatus 
        WHERE id = :operationId
    """)
    suspend fun updateOperationDetails(
        operationId: String,
        newAmount: Long,
        newCategoryId: String,
        newStorageId: String,
        newComment: String?,
        newDateTime: Long,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: SynStatus = SynStatus.PENDING
    )


    // --- DELETE (УДАЛЕНИЕ) ---

    @Query("""
        UPDATE operations 
        SET is_deleted = 1, updated_at = :timestamp, sync_status = :syncStatus 
        WHERE id = :operationId
    """)
    suspend fun softDeleteOperation(
        operationId: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: SynStatus = SynStatus.PENDING
    )

    // --- GUEST MIGRATION (МИГРАЦИЯ ГОСТЯ) ---

    @Query("""
        UPDATE operations 
        SET user_id = :newUserId, sync_status = :syncStatus, updated_at = :timestamp 
        WHERE user_id = 'GUEST'
    """)
    suspend fun bindGuestOperationsToUser(
        newUserId: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: SynStatus = SynStatus.PENDING
    )
}

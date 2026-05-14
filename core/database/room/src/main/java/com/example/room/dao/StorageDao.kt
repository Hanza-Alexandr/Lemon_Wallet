package com.example.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.room.entity.StorageRoomEntity
import com.example.room.entity.StorageWithColor
import kotlinx.coroutines.flow.Flow


@Dao
interface StorageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStorage(storage: StorageRoomEntity): Long

    @Update
    suspend fun updateStorage(storage: StorageRoomEntity)

    @Query("SELECT * FROM storage")
    fun getAllStorageData(): List<StorageRoomEntity>

    @Query("""
        SELECT 
            s.id, s.name, s.user_id, s.currency, s.type_storage, 
            s.note, s.color_id, s.is_statistics, s.is_archive, 
            c.hex_code AS color_hex, 
            c.user_id AS color_user_id
        FROM storage AS s
        LEFT JOIN color AS c ON s.color_id = c.id
        WHERE s.id = :id
    """)
    suspend fun getStorageWithColorById(id: Long): StorageWithColor?

    @Query("""
        SELECT 
            s.id, s.name, s.user_id, s.currency, s.type_storage, 
            s.note, s.color_id, s.is_statistics, s.is_archive, 
            c.hex_code AS color_hex, 
            c.user_id AS color_user_id
        FROM storage AS s
        LEFT JOIN color AS c ON s.color_id = c.id
    """)
    fun getAllStorageWithColorsFlow(): Flow<List<StorageWithColor>>

    @Delete
    suspend fun deleteStorage(storage: StorageRoomEntity)

    @Query("DELETE FROM storage WHERE id = :storageId")
    suspend fun deleteStorageDataById(storageId: Long)
}

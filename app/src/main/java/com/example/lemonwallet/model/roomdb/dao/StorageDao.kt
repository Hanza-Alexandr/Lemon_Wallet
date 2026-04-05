package com.example.lemonwallet.model.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lemonwallet.model.roomdb.entities.StorageRoomEntity
import com.example.lemonwallet.model.roomdb.entities.StorageWithColor
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageDao {
    @Insert(entity = StorageRoomEntity::class)
    fun insertNewStorageData(storage: StorageRoomEntity)

    @Query("SELECT * FROM storage")
    fun getAllStorageData(): List<StorageRoomEntity>

    @Query("""
        SELECT 
            s.id, s.name, s.user_id, s.currency, s.type_storage, 
            s.note, s.color_id, s.is_statistics, s.is_archive, 
            c.hex_code AS color_hex, 
            c.user_id AS color_user_id
        FROM storage AS s
        JOIN color AS c ON s.color_id = c.id
    """)
    fun getAllStorageWithColorsFlow(): Flow<List<StorageWithColor>>

    @Query("DELETE FROM storage WHERE id = :storageId")
    fun deleteStorageDataById(storageId: Long)
}


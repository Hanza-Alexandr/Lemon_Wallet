package com.example.lemonwallet.model.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lemonwallet.model.roomdb.entities.ColorRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Query("SELECT * FROM color")
    fun getAllColorsFlow(): Flow<List<ColorRoomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: ColorRoomEntity): Long

    @Query("SELECT * FROM color WHERE id = :id")
    suspend fun getColorById(id: Long): ColorRoomEntity?

    @Delete
    suspend fun deleteColor(color: ColorRoomEntity)
}

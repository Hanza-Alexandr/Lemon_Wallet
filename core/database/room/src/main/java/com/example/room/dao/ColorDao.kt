package com.example.room.dao

import androidx.room.*
import com.example.room.entity.ColorRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {

    // --- READ (ЧТЕНИЕ) ---

    // Получить поток всех активных цветов конкретного пользователя (или гостя)
    @Query("SELECT * FROM colors WHERE user_id = :userId AND is_deleted = 0")
    fun getAllColorsFlow(userId: String): Flow<List<ColorRoomEntity>>

    // Получить один конкретный цвет по его ID (однократно в фоне)
    @Query("SELECT * FROM colors WHERE id = :colorId AND is_deleted = 0 LIMIT 1")
    suspend fun getColorById(colorId: String): ColorRoomEntity?


    // --- CREATE (СОЗДАНИЕ) ---

    // Добавить один цвет или обновить его, если ID совпал
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: ColorRoomEntity)


    // --- UPDATE (ОБНОВЛЕНИЕ) ---
    // Точечное обновление HEX-кода цвета
    @Query("""
        UPDATE colors 
        SET hex_code = :newHex, updated_at = :timestamp, sync_status = :syncStatus 
        WHERE id = :colorId
    """)
    suspend fun updateColorHex(
        colorId: String,
        newHex: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: String = "PENDING"
    )

    // --- DELETE (ЛОГИЧЕСКОЕ УДАЛЕНИЕ) ---

    // Мягкое удаление цвета (помечаем как удаленный)
    @Query("""
        UPDATE colors 
        SET is_deleted = 1, updated_at = :timestamp, sync_status = :syncStatus 
        WHERE id = :colorId
    """)
    suspend fun softDeleteColor(
        colorId: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: String = "PENDING"
    )


    // --- GUEST MIGRATION (СИНХРОНИЗАЦИЯ / МИГРАЦИЯ) ---

    // Переводим все цвета гостя на новый серверный ID
    @Query("""
        UPDATE colors 
        SET user_id = :newUserId, sync_status = :syncStatus, updated_at = :timestamp 
        WHERE user_id = 'GUEST'
    """)
    suspend fun bindGuestColorsToUser(
        newUserId: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: String = "PENDING"
    )
}

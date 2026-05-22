package com.example.room.dao

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.domain.utils.SynStatus
import com.example.room.entity.CategoryRoomEntity
import com.example.room.model.CategoryWithColor

@Dao
interface CategoryDao {

    // --- READ (ЧТЕНИЕ) ---

    @Transaction
    @Query("SELECT * FROM categories WHERE user_id = :userId AND is_deleted = 0")
    fun getAllWithColorFlow(userId: String): Flow<List<CategoryWithColor>>

    @Transaction
    @Query("SELECT * FROM categories WHERE id = :id AND is_deleted = 0 LIMIT 1")
    suspend fun getWithColorById(id: String): CategoryWithColor?

    @Transaction
    @Query("SELECT * FROM categories WHERE user_id = :userId AND parent_id IS NULL AND is_deleted = 0")
    fun getRootWithColorFlow(userId: String): Flow<List<CategoryWithColor>>

    @Transaction
    @Query("SELECT * FROM categories WHERE parent_id = :parentId AND is_deleted = 0")
    fun getChildrenWithColorFlow(parentId: String): Flow<List<CategoryWithColor>>

    // --- CREATE & UPDATE (СОЗДАНИЕ И ОБНОВЛЕНИЕ) ---

    // Добавить или полностью перезаписать категорию
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryRoomEntity)

    // Полное обновление объекта категории
    @Update
    suspend fun updateCategory(category: CategoryRoomEntity)

    // Точечное редактирование полей категории
    @Query("""
        UPDATE categories 
        SET name = :newName, icon = :newIcon,color_id = :newColorId, parent_id = :newParentId,
            updated_at = :timestamp, sync_status = :syncStatus 
        WHERE id = :categoryId
    """)
    suspend fun updateCategoryDetails(
        categoryId: String,
        newName: String,
        newIcon: String,
        newColorId: String,
        newParentId: String?,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: SynStatus = SynStatus.PENDING
    )


    // --- DELETE (УДАЛЕНИЕ) ---

    // Мягкое каскадное удаление (Room сам удалит подкатегории, так как в ForeignKey прописан CASCADE)
    @Query("""
        UPDATE categories 
        SET is_deleted = 1, updated_at = :timestamp, sync_status = :syncStatus 
        WHERE id = :categoryId
    """)
    suspend fun softDeleteCategory(
        categoryId: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: SynStatus = SynStatus.PENDING
    )


    // --- GUEST MIGRATION (МИГРАЦИЯ ГОСТЯ) ---

    // Перепривязка всех гостевых категорий к серверному userId при авторизации
    @Query("""
        UPDATE categories 
        SET user_id = :newUserId, sync_status = :syncStatus, updated_at = :timestamp 
        WHERE user_id = 'GUEST'
    """)
    suspend fun bindGuestCategoriesToUser(
        newUserId: String,
        timestamp: Long = System.currentTimeMillis(),
        syncStatus: SynStatus = SynStatus.PENDING
    )
}

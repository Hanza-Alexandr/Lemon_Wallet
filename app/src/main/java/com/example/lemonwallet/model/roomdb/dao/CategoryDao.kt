package com.example.lemonwallet.model.roomdb.dao

import androidx.room.*
import com.example.lemonwallet.model.roomdb.entities.CategoryRoomEntity
import com.example.lemonwallet.model.roomdb.entities.CategoryWithColor
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("""
        SELECT 
            cat.*, 
            col.hex_code AS color_hex, 
            col.user_id AS color_user_id
        FROM category AS cat
        JOIN color AS col ON cat.color_id = col.id
    """)
    fun getAllCategoriesWithColorFlow(): Flow<List<CategoryWithColor>>

    @Query("""
        SELECT 
            cat.*, 
            col.hex_code AS color_hex, 
            col.user_id AS color_user_id
        FROM category AS cat
        JOIN color AS col ON cat.color_id = col.id
        WHERE cat.id = :id
    """)
    suspend fun getCategoryWithColorById(id: Long): CategoryWithColor?

    @Query("""
        SELECT 
            cat.*, 
            col.hex_code AS color_hex, 
            col.user_id AS color_user_id
        FROM category AS cat
        JOIN color AS col ON cat.color_id = col.id
        WHERE cat.parent_category_id = :parentId
    """)
    fun getChildrenByParentWithColorFlow(parentId: Long): Flow<List<CategoryWithColor>>

    @Query("""
        SELECT 
            cat.*, 
            col.hex_code AS color_hex, 
            col.user_id AS color_user_id
        FROM category AS cat
        JOIN color AS col ON cat.color_id = col.id
        WHERE cat.parent_category_id IS NULL
    """)
    fun getRootCategoriesWithColorFlow(): Flow<List<CategoryWithColor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryRoomEntity): Long

    @Update
    suspend fun updateCategory(category: CategoryRoomEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryRoomEntity)
}

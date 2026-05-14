package com.example.room.repository

import android.util.Log
import com.example.domain.Category
import com.example.domain.CategoryStructure
import com.example.domain.ICategoryRepository
import com.example.domain.NewCategory
import com.example.domain.Owner
import com.example.room.dao.CategoryDao
import com.example.room.entity.CategoryRoomEntity
import com.example.room.entity.ColorRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRoomRepository @Inject constructor(
    private val categoryDao: CategoryDao
) : ICategoryRepository {

    override fun getAllFlow(): Flow<List<Category>> {
        return categoryDao.getAllCategoriesWithColorFlow().map { list ->
            list.map { item ->
                item.toDomain(
                    ColorRoomEntity(
                        item.colorId,
                        item.colorUserId,
                        item.colorHex!!
                    ).toDomain()
                )
            }
        }
    }

    override suspend fun getById(id: Long): Category? {
        return categoryDao.getCategoryWithColorById(id)?.let { item ->
            item.toDomain(
                ColorRoomEntity(
                    item.colorId,
                    item.colorUserId,
                    item.colorHex!!
                ).toDomain()
            )
        }
    }

    override fun getChildrenByParentFlow(parentId: Long): Flow<List<Category>> {
        return categoryDao.getChildrenByParentWithColorFlow(parentId).map { list ->
            list.map { item ->
                item.toDomain(
                    ColorRoomEntity(
                        item.colorId,
                        item.colorUserId,
                        item.colorHex!!
                    ).toDomain()
                )
            }
        }
    }

    override fun getRootCategoriesFlow(): Flow<List<Category>> {
        return categoryDao.getRootCategoriesWithColorFlow().map { list ->
            list.map { item ->
                item.toDomain(
                    ColorRoomEntity(
                        item.colorId,
                        item.colorUserId,
                        item.colorHex!!
                    ).toDomain()
                )
            }
        }
    }

    override suspend fun save(category: Category): Category? {
        val entity = CategoryRoomEntity(
            id = category.id,
            name = category.name,
            colorId = category.color.id,
            pathIcon = category.icon,
            need = category.need.name,
            isHide = category.isHidden,
            userId = category.owner.let { if (it is Owner.User) it.userId else null },
            parentCategoryId = (category.structure as? CategoryStructure.Child)?.parentId
        )

        try {
            categoryDao.updateCategory(entity)
            val category = getById(category.id)
            if (category != null) Log.e("CategoryRoomRepository", "Ошибка при получении только что обновленной категории")
            return category
        }
        catch (e: Exception){
            Log.e("CategoryRoomRepository", "Ошибка при обновлении категории", e)
            return null
        }
    }

    override suspend fun save(category: NewCategory): Category? {
        val entity = CategoryRoomEntity(
            name = category.name,
            colorId = category.color.id,
            pathIcon = category.icon,
            need = category.need.name,
            isHide = category.isHidden,
            userId = category.owner.userId,
            parentCategoryId = (category.structure as? CategoryStructure.Child)?.parentId
        )
        try {
            val newId = categoryDao.insertCategory(entity)
            val category = getById(newId)
            if (category != null) Log.e("CategoryRoomRepository", "Ошибка при получении только что созданной категории")
            return category
        }
        catch (e: Exception){
            Log.e("CategoryRoomRepository", "Ошибка при создании категории", e)
            return null
        }

    }

    override suspend fun delete(category: Category): Category? {
        val entity = CategoryRoomEntity(
            id = category.id,
            name = category.name,
            colorId = category.color.id,
            pathIcon = category.icon,
            need = category.need.name,
            isHide = category.isHidden,
            userId = category.owner.let { if (it is Owner.User) it.userId else null },
            parentCategoryId = (category.structure as? CategoryStructure.Child)?.parentId
        )
        try {
            categoryDao.deleteCategory(entity)
            return category
        }
        catch (e: Exception){
            Log.e("CategoryRoomRepository", "Ошибка при удалении категории", e)
            return null
        }
    }
}
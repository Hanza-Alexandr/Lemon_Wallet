package com.example.room.repository

import com.example.domain.domainmodel.DomainCategory
import com.example.domain.reposytory.ICategoryRepository
import com.example.domain.domainmodel.NewDomainCategory
import com.example.room.dao.CategoryDao
import com.example.room.entity.toRoomEntity
import com.example.room.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRoomRepository @Inject constructor(
    private val categoryDao: CategoryDao
) : ICategoryRepository {
    override fun getAllCategoriesFlow(userId: String): Flow<List<DomainCategory>> {
        return categoryDao.getAllWithColorFlow(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getCategoryById(id: String): DomainCategory? {
        return categoryDao.getWithColorById(id)?.toDomain()
    }

    override fun getRootCategoriesFlow(userId: String): Flow<List<DomainCategory>> {
        return categoryDao.getRootWithColorFlow(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getChildrenByParentFlow(parentId: String): Flow<List<DomainCategory>> {
        return categoryDao.getChildrenWithColorFlow(parentId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun saveCategory(category: NewDomainCategory) {
        categoryDao.insertCategory(category.toRoomEntity())
    }

    override suspend fun updateCategory(category: DomainCategory) {
        categoryDao.updateCategory(category.toRoomEntity())
    }

    override suspend fun deleteCategory(id: String): Boolean {
        try {
            categoryDao.softDeleteCategory(id)
            return true
        }
        catch (e: Exception){
            return false
        }
    }

    override suspend fun migrateGuestCategories(newUserId: String) {
        TODO("Not yet implemented")
    }
}
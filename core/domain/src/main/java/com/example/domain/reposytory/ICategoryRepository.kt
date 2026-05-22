package com.example.domain.reposytory

import com.example.domain.domainmodel.DomainCategory
import com.example.domain.domainmodel.NewDomainCategory
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {
    fun getAllCategoriesFlow(userId: String): Flow<List<DomainCategory>>

    /**
     * Получить категорию по ID.
     */
    suspend fun getCategoryById(id: String): DomainCategory?

    /**
     * Получить только корневые категории (у которых parentId == null).
     */
    fun getRootCategoriesFlow(userId: String): Flow<List<DomainCategory>>

    /**
     * Получить список подкатегорий для конкретного родителя.
     */
    fun getChildrenByParentFlow(parentId: String): Flow<List<DomainCategory>>

    /**
     * Создать новую категорию.
     * Принимает NewCategory, возвращает созданную DomainCategory с ID.
     */
    suspend fun saveCategory(category: NewDomainCategory)

    /**
     * Обновить существующую категорию.
     */
    suspend fun updateCategory(category: DomainCategory)

    /**
     * Удалить категорию.
     * Реализация должна учитывать soft-delete и, возможно,
     * рекурсивное удаление подкатегорий.
     */
    suspend fun deleteCategory(id: String): Boolean

    /**
     * Миграция категорий гостя на аккаунт пользователя.
     */
    suspend fun migrateGuestCategories(newUserId: String)
}


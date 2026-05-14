package com.example.domain

import kotlinx.coroutines.flow.Flow

interface IStorageRepository {
    fun getAll(): Flow<List<Storage>>
    suspend fun getById(id: Long): Storage?
    suspend fun save(storage: Storage): Storage?
    suspend fun save(storage: NewStorage): Storage?
    suspend fun delete(storage: Storage): Storage?
}

interface IColorRepository {
    fun getAllFlow(): Flow<List<ExistColor>>
    suspend fun getById(id: Long): ExistColor?
    suspend fun update(color: UserColor): UserColor?
    suspend fun save(color: NewColor): ExistColor?
    suspend fun delete(color: UserColor): UserColor?
}

interface ICategoryRepository {
    fun getAllFlow(): Flow<List<Category>>
    suspend fun getById(id: Long): Category?
    fun getChildrenByParentFlow(parentId: Long): Flow<List<Category>>
    fun getRootCategoriesFlow(): Flow<List<Category>>
    suspend fun save(category: Category): Category?
    suspend fun save(category: NewCategory): Category?
    suspend fun delete(category: Category): Category?
}

interface IUserSettingsRepository {
    val userIdFlow: Flow<Int?>
    val isFirstOpeningApp: Flow<Boolean>
    val indexesSelectedStorageFlow: Flow<Set<Int>>
    suspend fun logIn(id: Int)
    suspend fun logOut()
    suspend fun markFirstAppOpeningCompleted()
    suspend fun saveSelectedIds(indexes: Set<Int>)
}

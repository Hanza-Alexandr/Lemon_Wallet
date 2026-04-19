package com.example.lemonwallet.model.repository

import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.NewColor
import com.example.lemonwallet.model.domain.NewStorage
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.UserColor
import kotlinx.coroutines.flow.Flow

interface IStorageRepository{
    fun getAll(): Flow<List<Storage>>
    suspend fun getById(id: Long): Storage?
    suspend fun save(storage: Storage): Storage?
    suspend fun save(storage: NewStorage): Storage?
    suspend fun delete(storage: Storage): Storage?
}

interface IColorRepository {
    fun getAllFlow(): Flow<List<ExistColor>>
    suspend fun getById(id: Long): ExistColor?
    suspend fun save(color: UserColor): ExistColor?
    suspend fun save(color: NewColor): ExistColor?
    suspend fun delete(color: UserColor): Boolean
}
/**
interface IOperationRepository{
    fun getAll(): List<Operation>
    fun getOperationsByStorage(storageId: Long): List<Operation>
    fun getOperationById(id: Long): GeneralTransaction?
    fun getTransferById(id: Long): TransferTransaction?
    fun save(newOperation: NewOperation): Operation?
    fun save(operation: Operation): Operation?
    fun delete(operation: Operation): Operation?
}
interface IColorRepository{
    fun getByHex(hex: String): ExistColor?
    fun getById(id: Long): ExistColor?
    fun getAll(): List<ExistColor>
    fun save(color: UserColor): UserColor?
    fun save(color: NewColor): UserColor?
    fun delete(color: UserColor): UserColor?

    fun hasRelation(colorId: Long): Boolean
    fun replaceColorEverywhere(color: ExistColor, newColor: ExistColor): ExistColor?
}
interface ICategoryRepository{
    fun getBaseCategories(): List<Category>
    fun getChildrenByParent(parentCategoryId: Long?): List<Category>
    fun getById(id: Long): Category?
    fun save(category: Category): Category?
    fun save(category: NewCategory): Category?
    fun delete(id: Long): Category?
}
interface ISettingRepository{
    fun save(setting: Setting)
    fun load(): Setting
    fun removeAll()
}
interface ICurrentUserRepository {
    fun getCurrentUser(): User?
    fun setCurrentUser(user: User)
}
        */
package com.example.domain.reposytory

import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.NewDomainStorage
import kotlinx.coroutines.flow.Flow

interface IStorageRepository {
    /**
     * Получить поток всех активных счетов пользователя.
     * Автоматически обновляется при любых изменениях в БД.
     */
    fun getAllStoragesFlow(): Flow<List<DomainStorage>>

    /**
     * Получить конкретный счет по ID.
     * Используем String, если перешли на UUID, или Long, если оставили автоинкремент Room.
     */
    suspend fun getStorageById(id: String): DomainStorage?

    /**
     * Сохранить новый счет.
     * Принимает NewStorage (без ID), возвращает созданный DomainStorage (уже с ID).
     */
    suspend fun saveStorage(storage: NewDomainStorage)

    /**
     * Обновить существующий счет.
     */
    suspend fun updateStorage(storage: DomainStorage)

    /**
     * Удалить счет.
     * В реализации это будет soft-delete (is_deleted = 1),
     * чтобы не "сломать" историю связанных операций и переводов.
     */
    suspend fun deleteStorage(id: String): Boolean

    /**
     * Миграция счетов гостя на аккаунт пользователя.
     */
    suspend fun migrateGuestStorages(newUserId: String)
}
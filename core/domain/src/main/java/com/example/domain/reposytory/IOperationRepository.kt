package com.example.domain.reposytory

import com.example.domain.domainmodel.*
import kotlinx.coroutines.flow.Flow

interface IOperationRepository {

    /**
     * Получить общий поток ВСЕХ транзакций (доходы, расходы и переводы).
     * Возвращает List<DomainOperation>, который можно легко сортировать по дате.
     */
    fun getAllTransactionsFlow(): Flow<List<DomainOperation>>

    /**
     * Получить историю действий для конкретного счета (Storage).
     * Включает операции по этому счету и переводы, где он участвует как отправитель или получатель.
     */
    suspend fun getTransactionsByStorageFlow(storageId: String): Flow<List<DomainOperation>>

    /**
     * Получить одну операцию (доход/расход) по ID.
     */
    suspend fun getGeneralOperationById(id: String): GeneralOperation?

    /**
     * Получить один перевод по ID.
     */
    suspend fun getTransferOperationById(id: String): TransferOperation?

    /**
     * Сохранение новой обычной операции (доход или расход).
     */
    suspend fun saveGeneralOperation(operation: NewGeneralOperation)

    /**
     * Сохранение нового перевода между счетами.
     */
    suspend fun saveTransfer(transfer: NewTransferOperation)

    /**
     * Обновление существующей операции.
     */
    suspend fun updateGeneralOperation(operation: GeneralOperation)

    /**
     * Обновление существующего перевода.
     */
    suspend fun updateTransfer(transfer: TransferOperation)

    /**
     * Удаление любой транзакции.
     * Реализация в Data-слое должна сама понять, из какой таблицы удалять, исходя из ID.
     */
    suspend fun deleteTransaction(operation: DomainOperation): Boolean

    /**
     * Миграция всех операций и переводов гостя на аккаунт пользователя.
     */
    suspend fun migrateGuestData(newUserId: String)
}
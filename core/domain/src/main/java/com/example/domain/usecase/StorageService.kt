package com.example.domain.usecase

import com.example.domain.Currency
import com.example.domain.ExistColor
import com.example.domain.NewStorage
import com.example.domain.state.DomainState
import com.example.domain.Storage
import com.example.domain.TypeStorage
import com.example.domain.IStorageRepository
import com.example.domain.IUserSettingsRepository
import com.example.domain.state.AuthorizationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class StorageService @Inject constructor(
    private val storageRepo: IStorageRepository,
    private val userSettingsRepo: IUserSettingsRepository,
    private val accountService: AccountService
) : IStorageService {

    val stateSelectedStorages = combine(
        storageRepo.getAll(),
        userSettingsRepo.indexesSelectedStorageFlow
    ) { storages, savedState ->
        val validIndexes = storages.indices.toSet()
        savedState.intersect(validIndexes)
    }

    suspend fun onSelect(isLongClick: Boolean, index: Int) {
        val currentSelected = userSettingsRepo.indexesSelectedStorageFlow.first()
        val storages = storageRepo.getAll().first()

        val isAlreadySelected = currentSelected.contains(index)
        val isSelectModeActive = currentSelected.size > 1

        val newSelected = currentSelected.toMutableSet().apply {
            when {
                isLongClick -> {
                    if (isSelectModeActive) clear()
                    add(index)
                }
                isAlreadySelected -> {
                    if (size > 1) remove(index)
                }
                else -> {
                    if (size == 1) clear()
                    add(index)
                }
            }
        }

        val validIndexes = storages.indices.toSet()
        userSettingsRepo.saveSelectedIds(newSelected.intersect(validIndexes))
    }

    override fun getFlowStorageList(): Flow<List<Storage>> {
        return storageRepo.getAll()
    }

    override suspend fun getStorage(storageId: Long): DomainState<Storage> {
        storageRepo.getById(storageId).let {
            return when (it) {
                null -> DomainState.Error("Ошибка получения")
                else -> DomainState.Success(it)
            }
        }
    }

    override suspend fun createStorage(
        name: String,
        currency: Currency,
        typeStorage: TypeStorage,
        note: String?,
        color: ExistColor?
    ): DomainState<Storage> {
        val newStorageState = NewStorage.create(
            userId = accountService.stateAuth.first().let {
                when (it) {
                    is AuthorizationState.Authorization -> it.id.toLong()
                    else -> -1L
                }
            },
            name = name,
            currency = currency,
            typeStorage = typeStorage,
            note = note,
            color = color
        )
        val newStorage = when (newStorageState) {
            is DomainState.Success -> newStorageState.domain
            is DomainState.Error -> null
        }
        if (newStorage != null) {
            storageRepo.save(newStorage).let {
                return when (it) {
                    null -> DomainState.Error("Ошибка создания на стороне БД")
                    else -> DomainState.Success(it)
                }
            }
        }
        val message = when (newStorageState) {
            is DomainState.Success -> null
            is DomainState.Error -> newStorageState.message
        }
        return DomainState.Error("Ошибка создания на стороне ПРИЛОЖЕНИЯ $message")
    }

    override suspend fun updateStorage(
        changingStorage: Storage,
        name: String?,
        typeStorage: TypeStorage?,
        currency: Currency?,
        note: String?,
        color: ExistColor?,
        isStatistic: Boolean?,
        isArchive: Boolean?
    ): DomainState<Storage> {
        storageRepo.save(
            Storage(
                id = changingStorage.id,
                userId = changingStorage.userId,
                name = name ?: changingStorage.name,
                currency = currency ?: changingStorage.currency,
                typeStorage = typeStorage ?: changingStorage.typeStorage,
                note = note ?: changingStorage.note,
                color = color,
                isStatistics = isStatistic ?: changingStorage.isStatistics,
                isArchive = isArchive ?: changingStorage.isArchive
            )
        ).let {
            return when (it) {
                null -> DomainState.Error("Ошибка создания")
                else -> DomainState.Success(it)
            }
        }
    }

    override suspend fun deleteStorage(storage: Storage): DomainState<Storage> {
        storageRepo.delete(storage).let {
            return when (it) {
                null -> DomainState.Error("Ошибка удаления")
                else -> DomainState.Success(it)
            }
        }
    }

    override fun getStorageBalance(storage: Storage): DomainState<Double> {
        return when (storage.id) {
            1L -> DomainState.Success(2000.0)
            2L -> DomainState.Success(5000.0)
            3L -> DomainState.Success(12345.34)
            else -> DomainState.Success(0.0)
        }
    }
}

package com.example.storage_block.usecases

import com.example.domain.reposytory.IStorageRepository
import com.example.domain.settings.ISettingsRepository
import com.example.domain.usecase.GetStorageBalanceUseCase
import com.example.storage_block.model.UiForStorageBlock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetFlowUiForStorageBlockUseCase @Inject constructor(
    private val getStorageBalanceUseCase: GetStorageBalanceUseCase,
    private val repo: IStorageRepository,
    private val settings: ISettingsRepository,
) {
    operator fun invoke(): Flow<List<UiForStorageBlock>> {
        // 1. Сначала подписываемся на userId, так как он нужен для получения списка стораджей
        return settings.userIdFlow.flatMapLatest { userId ->
            if (userId == null) throw NullPointerException("Нет UserID")

            // 2. Комбинируем поток всех стораджей и список выбранных ID
            combine(
                repo.getAllStoragesFlow(),
                settings.idSelectedStorageFlow
            ) { storages, selectedIds ->
                // 3. Маппим список стораджей в UI-модели
                storages.map { storage ->
                    UiForStorageBlock(
                        // Вызываем баланс (UseCase должен возвращать Long или Double)
                        balance = getStorageBalanceUseCase.balance(storage),
                        storage = storage,
                        isSelected = selectedIds.contains(storage.id)
                    )
                }
            }
        }
    }
}
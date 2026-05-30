package com.example.storage_block.usecases

import com.example.domain.reposytory.IStorageRepository
import com.example.domain.settings.ISettingsRepository
import com.example.domain.usecase.GetStorageBalanceUseCase
import com.example.storage_block.model.UiForStorageBlock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GetFlowUiForStorageBlockUseCase @Inject constructor(
    private val getStorageBalanceUseCase: GetStorageBalanceUseCase,
    private val storageRepo: IStorageRepository,
    private val settings: ISettingsRepository
) {

    // 1. Берем поток всех счетов
    private val storagesFlow = storageRepo.getAllStoragesFlow()

    // 2. Берем ОДИН поток, который считает балансы сразу для всех счетов
    private val allBalancesFlow = getStorageBalanceUseCase.allBalancesFlow()
    private val selectIdsFlow = settings.idSelectedStorageFlow

    operator fun invoke(): Flow<List<UiForStorageBlock>> {
        // 3. Комбинируем их
        return combine(storagesFlow, allBalancesFlow, selectIdsFlow) { storages, balancesMap, selectIds->
            storages.map { storage ->
                UiForStorageBlock(
                    storage = storage,
                    balance = balancesMap[storage.id] ?: 0L,
                    isSelected = if (selectIds.find { it == storage.id } != null) true else false
                )
            }
        }.distinctUntilChanged()
    }
}
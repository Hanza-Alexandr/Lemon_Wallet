package com.example.last_operation_block

import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.reposytory.IOperationRepository
import com.example.domain.usecase.GetSelectStorageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class GetLastOperationsUseCase @Inject constructor(
    private val selectStorageUseCase: GetSelectStorageUseCase,
    private val operationRepository: IOperationRepository,
    // В Hilt для базовых типов (Int) может понадобиться @Named или предоставление через модуль

) {
    operator fun invoke(limitItem: Int): Flow<List<DomainOperation>> =
        operationRepository.getAllTransactionsFlow()
            .combine(selectStorageUseCase()) { operations, selectedStorages ->
                val selectedIds = selectedStorages.map { it.id }.toSet()

                operations
                    .asSequence() // Используем sequence для оптимизации (фильтр + тейк)
                    .filter { operation ->
                        when (operation) {
                            is GeneralOperation ->
                                operation.storage.id in selectedIds

                            is TransferOperation ->
                                operation.fromStorage.id in selectedIds ||
                                        operation.toStorage.id in selectedIds
                        }
                    }
                    // Если операции не отсортированы в репозитории,
                    // стоит добавить .sortedByDescending { it.date } перед take
                    .take(limitItem)
                    .toList()
            }
}
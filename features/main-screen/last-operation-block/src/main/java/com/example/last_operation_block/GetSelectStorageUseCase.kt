package com.example.last_operation_block

import android.util.Log
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.reposytory.IOperationRepository
import com.example.domain.reposytory.IStorageRepository
import com.example.domain.settings.ISettingsRepository
import com.example.domain.usecase.GetSelectStorageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject


class GetOperationsUseCase @Inject constructor(
    private val selectStorageUseCase: GetSelectStorageUseCase,
    private val operationRepository: IOperationRepository,
) {
    operator fun invoke(): Flow<List<DomainOperation>> =
        operationRepository.getAllTransactionsFlow()
            .combine(selectStorageUseCase()) { operations, selectedStorages ->
                val selectedIds = selectedStorages.map { it.id }.toSet()

                operations.filter { operation ->
                    when (operation) {
                        is GeneralOperation ->
                            operation.storage.id in selectedIds

                        is TransferOperation ->
                            operation.fromStorage.id in selectedIds ||
                                    operation.toStorage.id in selectedIds
                    }
                }
            }
}
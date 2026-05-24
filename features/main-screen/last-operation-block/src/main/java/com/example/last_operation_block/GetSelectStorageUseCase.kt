package com.example.last_operation_block

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
    operator fun invoke(): Flow<List<DomainOperation>>{
        val selectStorage = selectStorageUseCase.invoke()
        val allOperations = operationRepository.getAllTransactionsFlow()

        return allOperations.combine(selectStorage) { operations, storages ->
            operations.filter { operation ->
                storages.any { storage ->
                    when(operation){
                        is GeneralOperation -> {
                            operation.storage.id == storage.id
                        }
                        is TransferOperation -> {
                            operation.fromStorage.id == storage.id || operation.toStorage.id == storage.id
                        }
                    }
                }
            }
        }
    }
}
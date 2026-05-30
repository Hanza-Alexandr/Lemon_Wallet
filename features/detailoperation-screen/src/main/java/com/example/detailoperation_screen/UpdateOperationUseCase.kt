package com.example.detailoperation_screen

import com.example.detailoperation_screen.model.UIStatesDetailGeneralOperations
import com.example.detailoperation_screen.model.UiStateTypeOperation
import com.example.detailoperation_screen.model.toNewDomainOperation
import com.example.domain.domainmodel.CreditOperation
import com.example.domain.domainmodel.DebitOperation
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.domainmodel.NewGeneralOperation
import com.example.domain.domainmodel.NewTransferOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.reposytory.IOperationRepository
import jakarta.inject.Inject

class UpdateOperationUseCase @Inject constructor(
    private val operationRepository: IOperationRepository
) {
    suspend operator fun invoke(uiState: UIStatesDetailGeneralOperations, operation: DomainOperation): String?{// Если пусто то без ошибки
       if (uiState.uiStateTypeOperation ==null) return "Ошибка операции"
        val type = uiState.uiStateTypeOperation
        val uiOpType = when(type){
            is UiStateTypeOperation.TransferUiStateTypeOperation -> {
                TransferOperation::class
            }
            is UiStateTypeOperation.GeneralOperationUiStateTypeOperation -> {
                when(type.isDebit){
                    true -> DebitOperation::class
                    false -> CreditOperation::class
                }
            }
        }

        if (operation::class == uiOpType){
            when(operation){
                is TransferOperation -> {
                    uiState.uiStateTypeOperation as UiStateTypeOperation.TransferUiStateTypeOperation
                    val upTrans = TransferOperation(
                        id = operation.id,
                        userId = operation.id,
                        fromStorage = uiState.uiStateTypeOperation.fromStorageList.find { it.isSelected }?.storage
                            ?: return "Не выбран счет from",
                        toStorage = uiState.uiStateTypeOperation.toStorageList.find { it.isSelected }?.storage
                            ?: return "Не выбран счет to",
                        amount = uiState.result,
                        comment = uiState.note,
                        date = uiState.date,
                        time = uiState.time
                    )
                    operationRepository.updateTransfer(upTrans)
                }
                is DebitOperation -> {
                    uiState.uiStateTypeOperation as UiStateTypeOperation.GeneralOperationUiStateTypeOperation
                    val upDeb = DebitOperation(
                        id = operation.id,
                        userId = operation.userId,
                        storage = uiState.uiStateTypeOperation.storageList.find { it.isSelected }?.storage
                            ?: return "Не выбран счет",
                        category = uiState.uiStateTypeOperation.categories.find { it.isSelect }?.category
                            ?: return "Не выбрана категория",
                        amount = uiState.result,
                        comment = uiState.note,
                        date = uiState.date,
                        time = uiState.time
                    )
                    operationRepository.updateGeneralOperation(upDeb)
                }
                is CreditOperation -> {
                    uiState.uiStateTypeOperation as UiStateTypeOperation.GeneralOperationUiStateTypeOperation
                    val upCre = CreditOperation(
                        id = operation.id,
                        userId = operation.userId,
                        storage = uiState.uiStateTypeOperation.storageList.find { it.isSelected }?.storage
                            ?: return "Не выбран счет",
                        category = uiState.uiStateTypeOperation.categories.find { it.isSelect }?.category
                            ?: return "Не выбрана категория",
                        amount = uiState.result,
                        comment = uiState.note,
                        date = uiState.date,
                        time = uiState.time
                    )
                    operationRepository.updateGeneralOperation(upCre)
                }

                else -> {
                    return "Ошибка операции"
                }
            }
        }
        else if (operation::class == GeneralOperation::class && uiOpType == GeneralOperation::class ){
            TODO()
        }
        else{
            val newOp = uiState.toNewDomainOperation()
            if (newOp is NewGeneralOperation){
                operationRepository.saveGeneralOperation(newOp)
            }
            else if (newOp is NewTransferOperation){
                operationRepository.saveTransfer(newOp)
            }
            operationRepository.deleteTransaction(operation)
        }
        return null
    }
}
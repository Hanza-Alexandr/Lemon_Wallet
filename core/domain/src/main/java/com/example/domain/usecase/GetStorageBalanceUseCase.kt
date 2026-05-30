package com.example.domain.usecase

import com.example.domain.domainmodel.CreditOperation
import com.example.domain.domainmodel.DebitOperation
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.reposytory.IOperationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStorageBalanceUseCase @Inject constructor(
    private val operationRepo: IOperationRepository
){
    suspend fun balanceFlow(storage: DomainStorage): Flow<Long>{
       return operationRepo.getTransactionsByStorageFlow(storage.id)
            .map { allOperations ->
                // Вместо var res и цикла map лучше использовать fold или sumOf
                allOperations.fold(0L) { acc, operation ->
                    when (operation) {
                        is DebitOperation -> acc + operation.amount
                        is CreditOperation -> acc - operation.amount
                        is TransferOperation -> {
                            if (operation.fromStorage == storage) {
                                acc - operation.amount
                            } else {
                                acc + operation.amount
                            }
                        }
                        else -> acc // Если ветка else ничего не делает, просто возвращаем текущее значение
                    }
                }
            }
    }
    suspend fun balance(storage: DomainStorage): Long {
        val allOperation = operationRepo.getTransactionsByStorageFlow(storage.id).first()
        var res: Long = 0
        allOperation.map {
            when(it){
                is DebitOperation -> {
                    res+= it.amount
                }
                is CreditOperation -> {
                    res-= it.amount
                }
                is TransferOperation -> {
                    if (it.fromStorage == storage){
                        res-= it.amount
                    }
                    else{
                        res+= it.amount
                    }
                }

                else -> {

                }
            }
        }
        return res
    }
}
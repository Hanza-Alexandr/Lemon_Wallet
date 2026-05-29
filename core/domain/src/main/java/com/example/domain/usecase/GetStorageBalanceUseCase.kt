package com.example.domain.usecase

import com.example.domain.domainmodel.CreditOperation
import com.example.domain.domainmodel.DebitOperation
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.reposytory.IOperationRepository
import com.example.domain.reposytory.IStorageRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.random.Random

class GetStorageBalanceUseCase @Inject constructor(
    private val storageRepo: IStorageRepository,
    private val operationRepo: IOperationRepository
){
    suspend operator fun invoke(storage: DomainStorage): Long {
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
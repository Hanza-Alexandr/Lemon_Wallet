package com.example.room.repository

import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.domainmodel.NewGeneralOperation
import com.example.domain.domainmodel.NewTransferOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.reposytory.IOperationRepository
import com.example.domain.usecase.GetUserIdUseCase
import com.example.room.dao.OperationDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OperationRoomRepository @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val operationDao: OperationDao
) :
    IOperationRepository {
    override suspend fun getAllTransactionsFlow(): Flow<List<DomainOperation>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionsByStorageFlow(
        storageId: String
    ): Flow<List<DomainOperation>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGeneralOperationById(id: String): GeneralOperation? {
        TODO("Not yet implemented")
    }

    override suspend fun getTransferOperationById(id: String): TransferOperation? {
        TODO("Not yet implemented")
    }

    override suspend fun saveGeneralOperation(operation: NewGeneralOperation) {
        TODO("Not yet implemented")
    }

    override suspend fun saveTransfer(transfer: NewTransferOperation) {
        TODO("Not yet implemented")
    }

    override suspend fun updateGeneralOperation(operation: GeneralOperation) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTransfer(transfer: TransferOperation) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransaction(operation: DomainOperation): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun migrateGuestData(newUserId: String) {
        TODO("Not yet implemented")
    }

}
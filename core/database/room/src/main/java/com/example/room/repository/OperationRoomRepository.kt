package com.example.room.repository

import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.domainmodel.NewGeneralOperation
import com.example.domain.domainmodel.NewTransferOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.reposytory.IOperationRepository
import com.example.domain.usecase.GetUserIdUseCase
import com.example.room.dao.OperationDao
import com.example.room.dao.TransferDao
import com.example.room.entity.toDomain
import com.example.room.entity.toRoomEntity
import com.example.room.model.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class OperationRoomRepository @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val operationDao: OperationDao,
    private val transferDao: TransferDao,
) : IOperationRepository {
    override fun getAllTransactionsFlow(): Flow<List<DomainOperation>> {
        val generalOperation =  getUserIdUseCase.getIfFLow().flatMapLatest { userId ->
            operationDao.getAllOperationsWithDetailsFlow(userId).map { operations ->
                operations.map { it.toDomain() }
            }
        }

        val transferOperation =  getUserIdUseCase.getIfFLow().flatMapLatest { userId ->
            transferDao.getAllTransfersWithDetailsFlow(userId).map { operations ->
                operations.map { it.toDomain() }
            }
        }

        return generalOperation.combine(transferOperation) { general, transfer ->
            general + transfer
        }

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
        operationDao.insertOperation(operation.toRoomEntity())
    }

    override suspend fun saveTransfer(transfer: NewTransferOperation) {
        transferDao.insertTransfer(transfer.toRoomEntity())
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